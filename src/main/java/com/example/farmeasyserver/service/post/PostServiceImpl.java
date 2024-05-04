package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.dto.mainpage.ListCommunityDto;
import com.example.farmeasyserver.dto.mainpage.ListExperienceDto;
import com.example.farmeasyserver.dto.mainpage.ListMarketDto;
import com.example.farmeasyserver.dto.post.CreatePostRequest;
import com.example.farmeasyserver.dto.post.CreatePostResponse;
import com.example.farmeasyserver.dto.post.ListPostDto;
import com.example.farmeasyserver.dto.post.community.CommunityPostDto;
import com.example.farmeasyserver.dto.post.market.MarketPostDto;
import com.example.farmeasyserver.dto.post.experience.ExperiencePostDto;
import com.example.farmeasyserver.dto.post.community.CommunityPostRequest;
import com.example.farmeasyserver.dto.post.market.MarketPostRequest;
import com.example.farmeasyserver.dto.post.experience.ExperiencePostRequest;
import com.example.farmeasyserver.entity.board.Image;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import com.example.farmeasyserver.entity.board.exprience.Recruitment;
import com.example.farmeasyserver.entity.board.market.Item;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.UserRepository;
import com.example.farmeasyserver.repository.post.CommunityRepository;
import com.example.farmeasyserver.repository.post.ExperienceRepository;
import com.example.farmeasyserver.repository.post.MarketRepository;
import com.example.farmeasyserver.repository.post.PostRepository;
import com.example.farmeasyserver.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final MarketRepository marketRepository;
    private final ExperienceRepository experienceRepository;
    private final PostRepository postRepository;
    private final FileService fileService;
    @Value("${page.limit}")
    private int pageLimit;

    @Override
    public List<ListCommunityDto> getMainCommunityPosts() {
        List<CommunityPost> communityPosts = communityRepository.findTop5OrderByIdDesc();

        return communityPosts.stream()
                .map(ListCommunityDto::toDto)
                .toList();
    }

    @Override
    public List<ListMarketDto> getMainMarketPosts() {
        List<MarketPost> marketPosts = marketRepository.findTop4OrderByIdDesc();
        List<ListMarketDto> mainMarketPosts = marketPosts.stream()
                .map(ListMarketDto::toDto)
                .collect(toList());
        return imageMapping(marketPosts,mainMarketPosts);
    }

    @Override
    public List<ListExperienceDto> getMainExperiencePosts() {
        List<ExperiencePost> experiencePosts = experienceRepository.findTop4OrderByIdDesc();
        List<ListExperienceDto> mainExperiencePosts = experiencePosts.stream()
                .map(ListExperienceDto::toDto)
                .collect(toList());
        return imageMapping(experiencePosts,mainExperiencePosts);
    }

    @Override
    @Transactional
    public CreatePostResponse createCommunityPost(CommunityPostRequest req) throws ChangeSetPersister.NotFoundException {
        CommunityPost communityPost = createPost(new CommunityPost(req.getType()),req);
        communityRepository.save(communityPost);
        return new CreatePostResponse(communityPost.getId(),"community");
    }


    @Override
    @Transactional
    public CreatePostResponse createMarketPost(MarketPostRequest req) throws ChangeSetPersister.NotFoundException {
        Item item = new Item(req.getItemName(),req.getPrice(), req.getGram());
        MarketPost marketPost = createPost(new MarketPost(req.getContent(),item),req);
        marketRepository.save(marketPost);
        return new CreatePostResponse(marketPost.getId(),"market");
    }

    @Override
    @Transactional
    public CreatePostResponse createExperiencePost(ExperiencePostRequest req) throws ChangeSetPersister.NotFoundException {
        Recruitment recruitment = new Recruitment(
                req.getFarmName(),
                req.getStartTime(), req.getRecruitmentNum(),
                req.getDetailedRecruitmentCondition()
        );
        ExperiencePost post = createPost(new ExperiencePost(recruitment),req);
        experienceRepository.save(post);
        return new CreatePostResponse(post.getId(),"experience");
    }

    @Override
    public CommunityPostDto readCommunityPost(Long postId) throws ChangeSetPersister.NotFoundException {
        return CommunityPostDto.toDto(communityRepository.findByIdWithUser(postId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    public MarketPostDto readMarketPost(Long postId) throws ChangeSetPersister.NotFoundException {
        return MarketPostDto.toDto(marketRepository.findByIdWithUser(postId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    public ExperiencePostDto readExperiencePost(Long postId) throws ChangeSetPersister.NotFoundException {
        return ExperiencePostDto.toDto(experienceRepository.findByIdWithUser(postId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    public Slice<ListCommunityDto> getCommunityPostList(Pageable pageable) {
        Slice<CommunityPost> postSlice = communityRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        return postSlice.map(ListCommunityDto::toDto);
    }

    @Override
    public Slice<ListMarketDto> getMarketPostList(Pageable pageable,String sido,String sigungu) {
        Slice<MarketPost> postsPages;
        if (sigungu != null && !sigungu.isEmpty()) {
            //sigungu 값이 주어진 경우
            postsPages = marketRepository.findBySidoAndSigungu(PageRequest.of(pageable.getPageNumber(), pageLimit, Sort.by(Sort.Direction.DESC, "id")), sigungu);
        } else {
            //sigungu 값이 주어지지 않은 경우
            postsPages = marketRepository.findAllWithUser(PageRequest.of(pageable.getPageNumber(), pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        }
        List<Long> postIdList = postsPages.getContent().stream()
                .map(MarketPost::getId)
                .collect(toList());
        List<ImageDto> postImages= marketRepository.findImagesDtoByPostIds(postIdList);

        Slice<ListMarketDto> postsResponseSlice = postsPages.map(ListMarketDto::toDto);
        postsResponseSlice.forEach(p -> {
            List<ImageDto> imageDtos = groupImagesByPostId(postImages).get(p.getPostId());
            if(imageDtos != null && !imageDtos.isEmpty())
                p.setImage(imageDtos.get(0));
            }
        );
        return postsResponseSlice;
    }

    @Override
    public Slice<ListExperienceDto> getExperiencePostList(Pageable pageable,String sido,String sigungu) {
        Slice<ExperiencePost> postsPages;
        if (sigungu != null && !sigungu.isEmpty()) {
            // sido와 sigungu 값이 모두 주어진 경우
            postsPages = experienceRepository.findBySidoAndSigungu(PageRequest.of(pageable.getPageNumber(), pageLimit, Sort.by(Sort.Direction.DESC, "id")),sigungu);
        } else {
            // sido와 sigungu 값이 주어지지 않은 경우
            postsPages = experienceRepository.findAllWithUser(PageRequest.of(pageable.getPageNumber(), pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        }
        List<Long> postIdList = postsPages.getContent().stream()
                .map(ExperiencePost::getId)
                .collect(toList());
        List<ImageDto> postImages= experienceRepository.findImagesDtoByPostIds(postIdList);

        Slice<ListExperienceDto> postsResponseSlice = postsPages.map(ListExperienceDto::toDto);
        postsResponseSlice.forEach(p -> {
                    List<ImageDto> imageDtos = groupImagesByPostId(postImages).get(p.getPostId());
                    if(imageDtos != null && !imageDtos.isEmpty())
                        p.setImage(imageDtos.get(0));
                }
        );
        return postsResponseSlice;
    }

    public <T extends ListPostDto,R extends Post> List<T> imageMapping(List<R> postList,List<T> mainPageDto){
        List<Long> postIdList = postList.stream()
                .map(R::getId)
                .collect(toList());
        List<ImageDto> postImages = postRepository.findImagesDtoByPostIds(postIdList);
        mainPageDto.forEach(p -> {
            List<ImageDto> imageDtos = groupImagesByPostId(postImages).get(p.getPostId());
            if (imageDtos != null && !imageDtos.isEmpty())
                p.setImage(imageDtos.get(0));
        });
        return mainPageDto;
    }

    public <T extends Post> T createPost(T p, CreatePostRequest req) throws ChangeSetPersister.NotFoundException{
        User author = userRepository.findById(req.getUserId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<Image> imageList = req.getImageList().stream().map(i -> new Image(i.getOriginalFilename())).toList();

        p.setPostLike(req.getPostLike()); p.setTitle(req.getTitle());
        p.setContent(req.getContent());  p.setCropCategory(req.getCropCategory());
        p.setAuthor(author); p.addImages(imageList);

        uploadImages(p.getImageList(),req.getImageList());
        return p;
    }
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
