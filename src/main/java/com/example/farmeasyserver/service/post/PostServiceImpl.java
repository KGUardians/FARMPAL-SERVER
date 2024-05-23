package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.dto.post.*;
import com.example.farmeasyserver.dto.post.community.*;
import com.example.farmeasyserver.dto.post.experience.*;
import com.example.farmeasyserver.dto.mainpage.ListPostDto;
import com.example.farmeasyserver.dto.post.market.*;
import com.example.farmeasyserver.entity.board.Image;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.community.*;
import com.example.farmeasyserver.entity.board.exprience.*;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import com.example.farmeasyserver.entity.user.Role;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.UserJpaRepo;
import com.example.farmeasyserver.repository.post.PostJpaRepo;
import com.example.farmeasyserver.repository.post.community.*;
import com.example.farmeasyserver.repository.post.experience.*;
import com.example.farmeasyserver.repository.post.market.*;
import com.example.farmeasyserver.service.file.FileService;
import com.example.farmeasyserver.util.exception.user.UserException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final UserJpaRepo userJpaRepo;
    private final CommunityJpaRepo communityJpaRepo;
    private final CommunityRepo communityRepo;
    private final CommentJpaRepo commentJpaRepo;
    private final MarketJpaRepo marketJpaRepo;
    private final MarketRepo marketRepo;
    private final ExpJpaRepo expJpaRepo;
    private final ExpRepo expRepo;
    private final ExpAppJpaRepo expAppJpaRepo;
    private final PostJpaRepo postJpaRepo;
    private final FileService fileService;

    /*

    메인 페이지 게시글 리스트 조회

    */
    @Override
    public List<ListCommunityDto> getMainCommunityPostList() {
        List<CommunityPost> communityPostList = communityJpaRepo.findTop5OrderByIdDesc();
        return convertToCommunityDtoList(communityPostList);
    }
    private List<ListCommunityDto> convertToCommunityDtoList(List<CommunityPost> postList){
        return postList.stream()
                .map(ListCommunityDto::toDto)
                .toList();
    }

    @Override
    public List<ListMarketDto> getMainMarketPostList() {
        List<ListMarketDto> mainMarketPosts = marketRepo.findTop4OrderByIdDesc();
        imageMapping(mainMarketPosts); return mainMarketPosts;
    }

    @Override
    public List<ListExperienceDto> getMainExperiencePostList() {
        List<ListExperienceDto> mainExperiencePosts = expRepo.findTop4OrderByIdDesc();
        imageMapping(mainExperiencePosts); return mainExperiencePosts;
    }

    /*

    게시글 작성 메소드

    */
    @Override
    @Transactional
    public CreatePostResponse createCommunityPost(CommunityPostRequest req, CommunityType type, User author) {
        CommunityPost communityPost = createPost(CommunityPostRequest.toEntity(type),req, author);
        communityJpaRepo.save(communityPost);
        return new CreatePostResponse(communityPost.getId(),communityPost.getPostType());
    }

    @Override
    @Transactional
    public CreatePostResponse createMarketPost(MarketPostRequest req, User author) {
        MarketPost marketPost = createPost(MarketPostRequest.toEntity(req), req, author);
        marketJpaRepo.save(marketPost);
        return new CreatePostResponse(marketPost.getId(),marketPost.getPostType());
    }

    @Override
    @Transactional
    public CreatePostResponse createExperiencePost(ExperiencePostRequest req,User author) {
        ExperiencePost experiencePost = createPost(ExperiencePostRequest.toEntity(req),req, author);
        expJpaRepo.save(experiencePost);
        return new CreatePostResponse(experiencePost.getId(),experiencePost.getPostType());
    }

    /*

    게시글 작성 메소드

    */
    @Override
    @Transactional
    public Long deleteCommunityPost(Long postId, User user) {
        CommunityPost post = findCommunityPost(postId);
        deletePost(post,user);
        return postId;
    }

    @Override
    @Transactional
    public Long deleteMarketPost(Long postId, User user) {
        MarketPost post = findMarketPost(postId);
        deletePost(post,user);
        return postId;
    }

    @Override
    @Transactional
    public Long deleteExperiencePost(Long postId, User user) {
        ExperiencePost post = findExperiencePost(postId);
        deletePost(post,user);
        return postId;
    }

    /*

    게시판 게시글 조회 메소드

    */
    @Override
    public CommunityPostDto readCommunityPost(Long postId){
        return CommunityPostDto.toDto(findCommunityPost(postId));
    }

    @Override
    public MarketPostDto readMarketPost(Long postId){
        return MarketPostDto.toDto(findMarketPost(postId));
    }

    @Override
    public ExperiencePostDto readExperiencePost(Long postId){
        return ExperiencePostDto.toDto(findExperiencePost(postId));
    }

    @Override
    public CommunityPostDto updateCommunityPost(Long postId, UpdateComPostReq req, User user) {
        CommunityPost post = findCommunityPost(postId);
        updatePost(user, post, req);
        communityJpaRepo.save(post);
        return CommunityPostDto.toDto(post);
    }

    @Override
    public ExperiencePostDto updateExperiencePost(Long postId, UpdateExpPostReq req, User user) {
        ExperiencePost post = findExperiencePost(postId);
        updatePost(user, post, req);
        post.setRecruitment(UpdateExpPostReq.reqToRecruitment(req));
        expJpaRepo.save(post);
        return ExperiencePostDto.toDto(post);
    }

    @Override
    public MarketPostDto updateMarketPost(Long postId, UpdateMarPostReq req, User user) {
        MarketPost post = findMarketPost(postId);
        updatePost(user, post, req);
        post.setItem(UpdateMarPostReq.reqToItem(req));
        marketJpaRepo.save(post);
        return MarketPostDto.toDto(post);
    }

    /*

    각 게시판 게시글 리스트 조회 메소드

    */
    @Override
    public Slice<ListCommunityDto> getCommunityPostList(CommunityFilter filter, Pageable pageable) {
        Slice<ListCommunityDto> listResponse = communityRepo.findPostList(filter,pageable);
        imageMapping(listResponse.stream().toList()); return listResponse;
    }

    @Override
    public Slice<ListMarketDto> getMarketPostList(MarketFilter filter, Pageable pageable) {
        Slice<ListMarketDto> listResponse = marketRepo.findPostList(filter, pageable);
        imageMapping(listResponse.stream().toList()); return listResponse;
    }

    @Override
    public Slice<ListExperienceDto> getExperiencePostList(ExpFilter filter, Pageable pageable) {
        Slice<ListExperienceDto> listResponse = expRepo.findPostList(filter,pageable);
        imageMapping(listResponse.stream().toList()); return listResponse;
    }


    /*

    농촌체험 신청

    */

    @Override
    public ExpApplicationPageDto getExpAppPage(Long postId) {
        ExperiencePost post = findExperiencePost(postId);
        return ExpApplicationPageDto.toDto(post);
    }
    @Override
    @Transactional
    public ExpApplicationRequest requestExpApp(Long postId, ExpApplicationRequest req, User user) throws Exception {
        ExperiencePost experiencePost = findExperiencePost(postId);
        validateParticipants(experiencePost, req.getParticipants());
        User applicant = findUser(user.getId());
        processApplication(experiencePost, applicant, req.getParticipants());
        return req;
    }

    private void validateParticipants(ExperiencePost post, int participants) throws Exception {
        int availableNum = post.getRecruitment().getRecruitmentNum();
        if(availableNum < participants){
            throw new Exception("인원이 초과되었습니다.");
        }
    }

    private void processApplication(ExperiencePost post, User applicant, int participants){
        ExpApplication expApplication = new ExpApplication(participants,applicant,post);
        int remainingNum = post.getRecruitment().getRecruitmentNum() - participants;
        post.getRecruitment().setRecruitmentNum(remainingNum);
        expAppJpaRepo.save(expApplication);
    }

    @Override
    @Transactional
    public CommentRequest requestComment(Long postId, CommentRequest req, User user) {
        CommunityPost post = findCommunityPost(postId);
        User author = findUser(user.getId());
        Comment comment = new Comment(req.getComment(),post,author);
        commentJpaRepo.save(comment);
        return req;
    }

    /*

    리스트 이미지 매핑

    */
    private <T extends ListPostDto> void imageMapping(List<T> mainPageDto){
        List<Long> postIdList = extractPostIdList(mainPageDto);
        List<ImageDto> postImages = fetchPostImageList(postIdList);
        mapImageToPostList(mainPageDto,postImages);
    }
    private <T extends ListPostDto> List<Long> extractPostIdList(List<T> pageDto){
        return pageDto.stream()
                .map(T::getPostId)
                .toList();
    }
    private List<ImageDto> fetchPostImageList(List<Long> postIdList){
        return postJpaRepo.findImagesDtoByPostIds(postIdList);
    }
    private <T extends ListPostDto> void mapImageToPostList(List<T> postListDto, List<ImageDto> postImageList){
        Map<Long, List<ImageDto>> imagesByPostId = groupImagesByPostId(postImageList);
        postListDto.forEach(p -> {
            List<ImageDto> imageDtos = imagesByPostId.get(p.getPostId());
            if (imageDtos != null && !imageDtos.isEmpty()) {
                p.setImage(imageDtos.get(0));
            }
        });
    }
    private Map<Long, List<ImageDto>> groupImagesByPostId(List<ImageDto> postImageList) {
        return postImageList.stream()
                .collect(Collectors.groupingBy(ImageDto::getPostId));
    }


    /*

    게시글 작성 메소드

    */
    private <T extends Post> T createPost(T p, CreatePostRequest req, User user) {
        User author = userJpaRepo.findByIdWithFarm(user.getId()).orElseThrow();
        p.createPostFromReq(req, author);
        uploadImages(p.getImageList(),req.getImageList());
        return p;
    }

    private void updatePost(User user, Post post, UpdatePostRequest req){
        if(checkUser(user,post.getAuthor().getId())){
            ImageUpdateResult result = post.updatePostFromReq(req);
            deleteImages(result.getDeletedImageList());
            uploadImages(result.getAddedImageList(),result.getAddedImageFileList());
        }else throw new UserException("삭제할 권한이 없습니다.", HttpStatus.BAD_REQUEST);
    }

    private void uploadImages(List<Image> images, List<MultipartFile> fileImages) {
        IntStream.range(0, images.size()).forEach(i -> {
            try {
                fileService.upload(fileImages.get(i), images.get(i).getUniqueName());
            } catch (FileUploadException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void deleteImages(List<Image> images){
        images.stream().forEach(i -> fileService.delete(i.getUniqueName()));
    }

    private void deletePost(Post post, User user){
        if(checkUser(user,post.getAuthor().getId())){
            deleteImages(post.getImageList());
            postJpaRepo.delete(post);
        }else throw new UserException("삭제할 권한이 없습니다.", HttpStatus.BAD_REQUEST);
    }

    private boolean checkUser(User user, Long authorId){
        return user.getRole().equals(Role.ADMIN) || user.getId().equals(authorId);
    }

    private CommunityPost findCommunityPost(Long postId){
        return communityJpaRepo.findByIdWithUser(postId).orElseThrow();
    }
    private ExperiencePost findExperiencePost(Long postId){
        return expJpaRepo.findByIdWithUser(postId).orElseThrow();
    }
    private MarketPost findMarketPost(Long postId){
        return marketJpaRepo.findByIdWithUser(postId).orElseThrow();
    }
    private User findUser(Long userId){
        return userJpaRepo.findById(userId).orElseThrow();
    }

}
