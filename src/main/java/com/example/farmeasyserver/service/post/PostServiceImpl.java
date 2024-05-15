package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.dto.post.community.CommentRequest;
import com.example.farmeasyserver.dto.post.community.ListCommunityDto;
import com.example.farmeasyserver.dto.post.experience.ListExperienceDto;
import com.example.farmeasyserver.dto.post.market.ListMarketDto;
import com.example.farmeasyserver.dto.post.CreatePostRequest;
import com.example.farmeasyserver.dto.post.CreatePostResponse;
import com.example.farmeasyserver.dto.mainpage.ListPostDto;
import com.example.farmeasyserver.dto.post.community.CommunityPostDto;
import com.example.farmeasyserver.dto.post.experience.ExpApplicationRequest;
import com.example.farmeasyserver.dto.post.market.MarketPostDto;
import com.example.farmeasyserver.dto.post.experience.ExperiencePostDto;
import com.example.farmeasyserver.dto.post.community.CommunityPostRequest;
import com.example.farmeasyserver.dto.post.market.MarketPostRequest;
import com.example.farmeasyserver.dto.post.experience.ExperiencePostRequest;
import com.example.farmeasyserver.entity.board.Image;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.PostType;
import com.example.farmeasyserver.entity.board.community.Comment;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import com.example.farmeasyserver.entity.board.exprience.ExpApplication;
import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import com.example.farmeasyserver.entity.board.exprience.Recruitment;
import com.example.farmeasyserver.entity.board.market.Item;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.UserJpaRepo;
import com.example.farmeasyserver.repository.post.*;
import com.example.farmeasyserver.repository.post.community.CommentJpaRepo;
import com.example.farmeasyserver.repository.post.community.CommunityFilter;
import com.example.farmeasyserver.repository.post.community.CommunityRepo;
import com.example.farmeasyserver.repository.post.community.CommunityJpaRepo;
import com.example.farmeasyserver.repository.post.experience.ExpAppJpaRepo;
import com.example.farmeasyserver.repository.post.experience.ExpFilter;
import com.example.farmeasyserver.repository.post.experience.ExpRepo;
import com.example.farmeasyserver.repository.post.experience.ExpJpaRepo;
import com.example.farmeasyserver.repository.post.market.MarketFilter;
import com.example.farmeasyserver.repository.post.market.MarketRepo;
import com.example.farmeasyserver.repository.post.market.MarketJpaRepo;
import com.example.farmeasyserver.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.CharacterCodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

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
    public List<ListCommunityDto> getMainCommunityPosts() {
        List<CommunityPost> communityPosts = communityJpaRepo.findTop5OrderByIdDesc();

        return communityPosts.stream()
                .map(ListCommunityDto::toDto)
                .toList();
    }

    @Override
    public List<ListMarketDto> getMainMarketPosts() {
        List<ListMarketDto> mainMarketPosts = marketRepo.findTop4OrderByIdDesc();
        imageMapping(mainMarketPosts); return mainMarketPosts;
    }

    @Override
    public List<ListExperienceDto> getMainExperiencePosts() {
        List<ListExperienceDto> mainExperiencePosts = expRepo.findTop4OrderByIdDesc();
        imageMapping(mainExperiencePosts); return mainExperiencePosts;
    }

    /*

    게시글 작성 메소드

    */
    @Override
    @Transactional
    public CreatePostResponse createCommunityPost(CommunityPostRequest req, CommunityType type) throws ChangeSetPersister.NotFoundException {
        CommunityPost communityPost = createPost(new CommunityPost(type),req);
        communityPost.setPostType(PostType.COMMUNITY);
        communityJpaRepo.save(communityPost);
        return new CreatePostResponse(communityPost.getId(),"community");
    }


    @Override
    @Transactional
    public CreatePostResponse createMarketPost(MarketPostRequest req) throws ChangeSetPersister.NotFoundException {
        Item item = new Item(req.getItemName(),req.getPrice(), req.getGram());
        MarketPost marketPost = createPost(new MarketPost(req.getContent(),item),req);
        marketPost.setPostType(PostType.MARKET);
        marketJpaRepo.save(marketPost);
        return new CreatePostResponse(marketPost.getId(),"market");
    }

    @Override
    @Transactional
    public CreatePostResponse createExperiencePost(ExperiencePostRequest req) throws ChangeSetPersister.NotFoundException {
        Recruitment recruitment = new Recruitment(
                req.getStartTime(), req.getRecruitmentNum(),
                req.getDetailedRecruitmentCondition()
        );
        ExperiencePost experiencePost = createPost(new ExperiencePost(recruitment),req);
        experiencePost.setPostType(PostType.EXPERIENCE);
        expJpaRepo.save(experiencePost);
        return new CreatePostResponse(experiencePost.getId(),"experience");
    }

    /*

    게시판 게시글 조회 메소드

    */
    @Override
    public CommunityPostDto readCommunityPost(Long postId) throws ChangeSetPersister.NotFoundException {
        return CommunityPostDto.toDto(communityJpaRepo.findByIdWithUser(postId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    public MarketPostDto readMarketPost(Long postId) throws ChangeSetPersister.NotFoundException {
        return MarketPostDto.toDto(marketJpaRepo.findByIdWithUser(postId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    public ExperiencePostDto readExperiencePost(Long postId) throws ChangeSetPersister.NotFoundException {
        return ExperiencePostDto.toDto(expJpaRepo.findByIdWithUser(postId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
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
    public ExpApplicationRequest requestExperience(ExpApplicationRequest req) throws Exception {
        ExperiencePost experiencePost = expJpaRepo.findById(req.getPostId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        // 신청 가능한 인원 확인
        int num = experiencePost.getRecruitment().getRecruitmentNum();
        if (num < req.getParticipants()) {
            throw new Exception("인원이 충분하지 않습니다.");
        }
        // 사용자 확인
        User applicant = userJpaRepo.findByUsernameAndPhoneNumber(req.getName(), req.getPhoneNumber())
                .orElseThrow(CharacterCodingException::new);

        ExpApplication expApplication = new ExpApplication();
        expApplication.setApplicants(applicant);
        expApplication.setPost(experiencePost);
        experiencePost.getRecruitment().setRecruitmentNum(num - req.getParticipants());
        expAppJpaRepo.save(expApplication);
        return req;
    }

    @Override
    public CommentRequest requestComment(Long postId, CommentRequest req) throws ChangeSetPersister.NotFoundException {
        CommunityPost post = communityJpaRepo.findById(postId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        User author = userJpaRepo.findById(req.getAuthorId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Comment comment = new Comment(req.getComment());
        comment.setPost(post);
        comment.setAuthor(author);
        commentJpaRepo.save(comment);
        return req;
    }

    /*

    리스트 이미지 매핑

    */
    public <T extends ListPostDto> void imageMapping(List<T> mainPageDto){
        List<Long> postIdList = mainPageDto.stream()
                .map(T::getPostId)
                .collect(toList());
        List<ImageDto> postImages = postJpaRepo.findImagesDtoByPostIds(postIdList);
        mainPageDto.forEach(p -> {
            List<ImageDto> imageDtos = groupImagesByPostId(postImages).get(p.getPostId());
            if (imageDtos != null && !imageDtos.isEmpty())
                p.setImage(imageDtos.get(0));
        });
    }

    /*

    게시글 작성 메소드

    */
    public <T extends Post> T createPost(T p, CreatePostRequest req) throws ChangeSetPersister.NotFoundException{
        User author = userJpaRepo.findById(req.getUserId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<Image> imageList = req.getImageList().stream().map(i -> new Image(i.getOriginalFilename())).toList();

        p.setPostLike(req.getPostLike()); p.setTitle(req.getTitle());
        p.setContent(req.getContent());  p.setCropCategory(req.getCropCategory());
        p.setAuthor(author); p.addImages(imageList);

        uploadImages(p.getImageList(),req.getImageList());
        return p;
    }

    /*

    각 게시글 이미지 매핑

    */
    private Map<Long, List<ImageDto>> groupImagesByPostId(List<ImageDto> postImages) {
        return postImages.stream()
                .collect(Collectors.groupingBy(ImageDto::getPostId));
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

}
