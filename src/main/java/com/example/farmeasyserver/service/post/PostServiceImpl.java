package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.dto.mainpage.MainCommunityDto;
import com.example.farmeasyserver.dto.mainpage.MainExperienceDto;
import com.example.farmeasyserver.dto.mainpage.MainMarketDto;
import com.example.farmeasyserver.dto.post.PostCreateResponse;
import com.example.farmeasyserver.dto.post.community.CommunityPostDto;
import com.example.farmeasyserver.dto.post.market.MarketPostDto;
import com.example.farmeasyserver.dto.post.experience.ExperiencePostDto;
import com.example.farmeasyserver.dto.post.community.CommunityRequest;
import com.example.farmeasyserver.dto.post.market.MarketRequest;
import com.example.farmeasyserver.dto.post.experience.ExperienceRequest;
import com.example.farmeasyserver.entity.board.Image;
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
import com.example.farmeasyserver.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    private final FileService fileService;
    @Override
    public List<MainCommunityDto> getMainCommunityPosts() {

        List<MainCommunityDto> mainCommunityPosts = new ArrayList<>();
        List<CommunityPost> communityPosts = communityRepository.findTop5OrderByIdDesc();
        List<Long> postIdList = communityRepository.findIdOrderByIdDesc();
        List<ImageDto> postImages = communityRepository.findTop5ImagesDto(postIdList);
        Map<Long,List<ImageDto>> postDtoMap = postImages.stream().collect(Collectors.groupingBy(ImageDto::getPostId));

        for(CommunityPost post : communityPosts){
            mainCommunityPosts.add(MainCommunityDto.toDto(post));
        }

        mainCommunityPosts.forEach(p -> {
            List<ImageDto> imageDtos = postDtoMap.get(p.getPostId());
            if (imageDtos != null && !imageDtos.isEmpty())
                p.setImageDto(imageDtos.get(0));
        });
        return mainCommunityPosts;
    }

    @Override
    public List<MainMarketDto> getMainMarketPosts() {
        List<MainMarketDto> mainMarketPosts = new ArrayList<>();
        List<MarketPost> marketPosts = marketRepository.findTop5OrderByIdDesc();

        for(MarketPost post : marketPosts){
            mainMarketPosts.add(MainMarketDto.toDto(post));
        }
        return mainMarketPosts;
    }

    @Override
    public List<MainExperienceDto> getMainExperiencePosts() {

        List<MainExperienceDto> mainExperiencePosts = new ArrayList<>();
        List<ExperiencePost> experiencePosts = experienceRepository.findTop5OrderByIdDesc();
        for(ExperiencePost post : experiencePosts){
            mainExperiencePosts.add(MainExperienceDto.toDto(post));
        }
        return mainExperiencePosts;
    }


    @Override
    @Transactional
    public PostCreateResponse createCommunityPost(CommunityRequest req) throws ChangeSetPersister.NotFoundException {
        User author = userRepository.findById(req.getUserId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<Image> imageList = req.getImageList().stream().map(i -> new Image(i.getOriginalFilename())).collect(toList());
        CommunityPost communityPost = new CommunityPost(req.getTitle(), req.getType(), req.getContent());

        communityPost.setAuthor(author);
        communityPost.addImages(imageList);
        communityRepository.save(communityPost);

        uploadImages(communityPost.getImageList(),req.getImageList());
        return new PostCreateResponse(communityPost.getId(),"community");
    }

    @Override
    @Transactional
    public PostCreateResponse createMarketPost(MarketRequest req) throws ChangeSetPersister.NotFoundException {
        User author = userRepository.findById(req.getUserId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<Image> imageList = req.getImageList().stream().map(i -> new Image(i.getOriginalFilename())).collect(toList());
        Item item = new Item(req.getItemName(),req.getItemCategory(), req.getPrice(), req.getGram());
        MarketPost post = new MarketPost(req.getTitle(),req.getContent(),item);

        post.setAuthor(author);
        post.addImages(imageList);

        marketRepository.save(post);

        uploadImages(post.getImageList(),req.getImageList());
        return new PostCreateResponse(post.getId(),"market");
    }

    @Override
    @Transactional
    public PostCreateResponse createExperiencePost(ExperienceRequest req) throws ChangeSetPersister.NotFoundException {
        User author = userRepository.findById(req.getUserId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        List<Image> imageList = req.getImageList().stream()
                .map(i -> new Image(i.getOriginalFilename())).collect(toList());

        Recruitment recruitment = new Recruitment(
                req.getStartTime(), req.getRecruitmentNum(),
                req.getDetailedRecruitmentCondition()
        );

        ExperiencePost post = new ExperiencePost(req.getTitle(), recruitment);
        post.addImages(imageList);
        post.setAuthor(author);
        experienceRepository.save(post);

        uploadImages(post.getImageList(),req.getImageList());
        return new PostCreateResponse(post.getId(),"experience");
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
