package com.example.farmeasyserver.service.post;

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
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
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

        List<MainCommunityDto> communityPostsDto = new ArrayList<>();
        List<CommunityPost> communityPosts = communityRepository.findTop5OrderByIdDesc();

        ModelMapper modelMapper = new ModelMapper();
        for(CommunityPost post : communityPosts){
            MainCommunityDto dto = modelMapper.map(post, MainCommunityDto.class);
            communityPostsDto.add(dto);
        }
        return communityPostsDto;
    }

    @Override
    public List<MainMarketDto> getMainMarketPosts() {
        return null;
    }

    @Override
    public List<MainExperienceDto> getMainExperiencePosts() {
        return null;
    }


    @Override
    @Transactional
    public PostCreateResponse createCommunityPost(CommunityRequest req) throws ChangeSetPersister.NotFoundException {
        User author = userRepository.findById(req.getUserId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<Image> imageList = req.getImageList().stream().map(i -> new Image(i.getOriginalFilename())).collect(toList());

        CommunityPost post = communityRepository.save(
                new CommunityPost(author,req.getTitle(), req.getType(), req.getContent(), imageList));
        uploadImages(post.getImageList(),req.getImageList());
        return new PostCreateResponse(post.getId(),post.getCommunityType());
    }

    @Override
    @Transactional
    public PostCreateResponse createMarketPost(MarketRequest req) throws ChangeSetPersister.NotFoundException {
        User author = userRepository.findById(req.getUserId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<Image> imageList = req.getImageList().stream().map(i -> new Image(i.getOriginalFilename())).collect(toList());

        MarketPost post = marketRepository.save(
                new MarketPost(author,req.getTitle(),req.getContent(),
                        new Item(req.getItemName(),req.getItemCategory(), req.getPrice(), req.getGram()),
                        imageList)
        );

        uploadImages(post.getImageList(),req.getImageList());
        return new PostCreateResponse(post.getId(),post.getItem());
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

        ExperiencePost post = experienceRepository.save(
                new ExperiencePost(author,req.getTitle(), recruitment, imageList)
        );

        uploadImages(post.getImageList(),req.getImageList());
        return new PostCreateResponse(post.getId(),post.getFarmName());
    }

    @Override
    public CommunityPostDto readCommunityPost(Long postId) throws ChangeSetPersister.NotFoundException {
        return CommunityPostDto.toDto(communityRepository.findById(postId)
                        .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    public MarketPostDto readMarketPost(Long postId) throws ChangeSetPersister.NotFoundException {
        return MarketPostDto.toDto(marketRepository.findById(postId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    public ExperiencePostDto readExperiencePost(Long postId) throws ChangeSetPersister.NotFoundException {
        return ExperiencePostDto.toDto(experienceRepository.findById(postId)
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
