package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.dto.post.*;
import com.example.farmeasyserver.dto.post.community.*;
import com.example.farmeasyserver.dto.post.community.comment.CommentDto;
import com.example.farmeasyserver.dto.post.community.comment.CommentRequest;
import com.example.farmeasyserver.dto.post.experience.*;
import com.example.farmeasyserver.dto.mainpage.ListPostDto;
import com.example.farmeasyserver.dto.post.experience.expapplication.ExpApplicationPageDto;
import com.example.farmeasyserver.dto.post.experience.expapplication.ExpApplicationRequest;
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
        List<ListCommunityDto> list = convertToCommunityDtoList(communityPostList);
        commentMapping(list);
        return list;
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

    게시글 작성

    */
    @Override
    @Transactional
    public CreatePostResponse createCommunityPost(CommunityPostRequest req, User author) {
        CommunityPost communityPost = createPost(CommunityPostRequest.toEntity(req.getCommunityType()),req, author);
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

    게시글 삭제

    */
    @Override
    @Transactional
    public Long deleteCommunityPost(Long postId, User author) {
        CommunityPost post = findCommunityPost(postId);
        deletePost(post,author);
        return postId;
    }

    @Override
    @Transactional
    public Long deleteMarketPost(Long postId, User author) {
        MarketPost post = findMarketPost(postId);
        deletePost(post,author);
        return postId;
    }

    @Override
    @Transactional
    public Long deleteExperiencePost(Long postId, User author) {
        ExperiencePost post = findExperiencePost(postId);
        deletePost(post,author);
        return postId;
    }

    /*

    게시글 조회 메소드

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

    /*

    게시글 수정

    */
    @Override
    public CommunityPostDto updateCommunityPost(Long postId, UpdateComPostReq req, User author) {
        CommunityPost post = findCommunityPost(postId);
        updatePost(author, post, req);
        post.setCommunityType(req.getType());
        communityJpaRepo.save(post);
        return CommunityPostDto.toDto(post);
    }

    @Override
    public ExperiencePostDto updateExperiencePost(Long postId, UpdateExpPostReq req, User author) {
        ExperiencePost post = findExperiencePost(postId);
        updatePost(author, post, req);
        post.setRecruitment(UpdateExpPostReq.reqToRecruitment(req));
        expJpaRepo.save(post);
        return ExperiencePostDto.toDto(post);
    }

    @Override
    public MarketPostDto updateMarketPost(Long postId, UpdateMarPostReq req, User author) {
        MarketPost post = findMarketPost(postId);
        updatePost(author, post, req);
        post.setItem(UpdateMarPostReq.reqToItem(req));
        marketJpaRepo.save(post);
        return MarketPostDto.toDto(post);
    }

    /*

    각 게시판 게시글 리스트 조회

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

    농촌체험 신청 관련

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

    /*

    커뮤니티 게시글 댓글 작성

    */
    @Override
    @Transactional
    public CommentRequest requestComment(Long postId, CommentRequest req, User author) {
        CommunityPost post = findCommunityPost(postId);
        Comment comment = new Comment(req.getComment(),post,author);
        commentJpaRepo.save(comment);
        return req;
    }

    private void commentMapping(List<ListCommunityDto> mainPageDto){
        List<Long> postIdList = extractPostIdList(mainPageDto);
        List<CommentDto> commentList = fetchCommentDtoByPostIdList(postIdList);
        mapCommentToPostList(mainPageDto, commentList);
    }

    private List<CommentDto> fetchCommentDtoByPostIdList(List<Long> postIdList){
        return commentJpaRepo.findCommentDtoListByPostIds(postIdList);
    }

    private void mapCommentToPostList(List<ListCommunityDto> postListDto, List<CommentDto> postCommentList){
        Map<Long, List<CommentDto>> imagesByPostId = groupCommentByPostId(postCommentList);
        postListDto.forEach(p -> {
            List<CommentDto> CommentDtoList = imagesByPostId.get(p.getPostId());
            if (CommentDtoList != null && !CommentDtoList.isEmpty()) {
                p.setCommentCount(CommentDtoList.size());
            }
        });
    }

    private Map<Long, List<CommentDto>> groupCommentByPostId(List<CommentDto> postCommentList) {
        return postCommentList.stream()
                .collect(Collectors.groupingBy(CommentDto::getPostId));
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
            List<ImageDto> imageDtoList = imagesByPostId.get(p.getPostId());
            if (imageDtoList != null && !imageDtoList.isEmpty()) {
                p.setImage(imageDtoList.get(0));
            }
        });
    }
    private Map<Long, List<ImageDto>> groupImagesByPostId(List<ImageDto> postImageList) {
        return postImageList.stream()
                .collect(Collectors.groupingBy(ImageDto::getPostId));
    }


    private <T extends Post> T createPost(T p, CreatePostRequest req, User author) {
        p.createPostFromReq(req, author);
        uploadImageList(p.getImageList(),req.getImageList());
        return p;
    }

    private void deletePost(Post post, User author){
        checkUser(author,post.getAuthor().getId());
        deleteImageList(post.getImageList());
        postJpaRepo.delete(post);
    }

    private void updatePost(User author, Post post, UpdatePostRequest req){
        checkUser(author,post.getAuthor().getId());
        ImageUpdateResult result = post.updatePostFromReq(req);
        deleteImageList(result.getDeletedImageList());
        uploadImageList(result.getAddedImageList(),result.getAddedImageFileList());
    }

    private void uploadImageList(List<Image> images, List<MultipartFile> fileImages) {
        IntStream.range(0, images.size()).forEach(i -> {
            try {
                fileService.upload(fileImages.get(i), images.get(i).getUniqueName());
            } catch (FileUploadException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void deleteImageList(List<Image> images){
        images.stream().forEach(i -> fileService.delete(i.getUniqueName()));
    }

    private boolean isAuthorized(User user, Long authorId){
        return user.getRole().equals(Role.ADMIN) || user.getId().equals(authorId);
    }

    private void checkUser(User user, Long authorId){
        if(!isAuthorized(user,authorId)) throw new UserException("해당 권한이 없습니다.", HttpStatus.BAD_REQUEST);
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
