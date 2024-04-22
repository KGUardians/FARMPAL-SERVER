package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.mainpage.MainComPostDto;
import com.example.farmeasyserver.dto.mainpage.ImagePostDto;
import com.example.farmeasyserver.dto.post.PostCreateResponse;
import com.example.farmeasyserver.dto.post.community.CommunityPostDto;
import com.example.farmeasyserver.dto.post.market.MarketPostDto;
import com.example.farmeasyserver.dto.post.ruralexp.RuralExpPostDto;
import com.example.farmeasyserver.dto.post.community.CommunityRequest;
import com.example.farmeasyserver.dto.post.market.MarketRequest;
import com.example.farmeasyserver.dto.post.ruralexp.RuralExpRequest;
import com.example.farmeasyserver.entity.board.Image;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.PostType;
import com.example.farmeasyserver.entity.board.Recruitment;
import com.example.farmeasyserver.entity.board.item.Item;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.PostRepository;
import com.example.farmeasyserver.repository.jpa.PostJpaRepository;
import com.example.farmeasyserver.repository.jpa.UserJpaRepository;
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
    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostRepository postRepository;
    private final FileService fileService;
    @Override
    public List<MainComPostDto> getMainPageComPosts() {

        String postType = "COMMUNITY";
        List<MainComPostDto> communityPostsDto = new ArrayList<>();
        List<Post> communityPosts = postJpaRepository.findTop5ByPostTypeOrderByIdDesc(postType);

        ModelMapper modelMapper = new ModelMapper();
        for(Post post : communityPosts){
            MainComPostDto dto = modelMapper.map(post, MainComPostDto.class);
            communityPostsDto.add(dto);
        }

        return communityPostsDto;
    }

    @Override
    public List<ImagePostDto> getMainPageMarketPosts() {
        return postRepository.getMainPagePost("MARKET");
    }

    @Override
    public List<ImagePostDto> getMainPageRuralExpPosts() {
        return postRepository.getMainPagePost("RURAL_EXP");
    }

    @Override
    @Transactional
    public PostCreateResponse createCommunityPost(CommunityRequest req) throws ChangeSetPersister.NotFoundException {
        User author = userJpaRepository.findById(req.getUserId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<Image> imageList = req.getImageList().stream().map(i -> new Image(i.getOriginalFilename())).collect(toList());

        Post post = postJpaRepository.save(new Post(author,req.getTitle(), PostType.FREE, req.getContent(), imageList));
        uploadImages(post.getImageList(),req.getImageList());
        return new PostCreateResponse(post.getId(),post.getPostType());
    }

    @Override
    @Transactional
    public PostCreateResponse createMarketPost(MarketRequest req) throws ChangeSetPersister.NotFoundException {
        User author = userJpaRepository.findById(req.getUserId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<Image> imageList = req.getImageList().stream().map(i -> new Image(i.getOriginalFilename())).collect(toList());

        Post post = postJpaRepository.save(
                new Post(author,req.getTitle(),req.getPostType(), req.getContent(),
                        new Item(req.getItemName(),req.getItemCategory(), req.getPrice(), req.getGram()),
                        imageList)
        );

        uploadImages(post.getImageList(),req.getImageList());
        return new PostCreateResponse(post.getId(),post.getPostType());
    }

    @Override
    @Transactional
    public PostCreateResponse createRuralExpPost(RuralExpRequest req) throws ChangeSetPersister.NotFoundException {
        User author = userJpaRepository.findById(req.getUserId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<Image> imageList = req.getImageList().stream().map(i -> new Image(i.getOriginalFilename())).collect(toList());

        Post post = postJpaRepository.save(
                new Post(author,req.getTitle(),req.getPostType(), req.getContent(),
                        new Recruitment(
                                req.getStartTime(),
                                req.getRecruitmentNum(),
                                req.getDetailedRecruitmentCondition()),
                        imageList)
        );

        uploadImages(post.getImageList(),req.getImageList());
        return new PostCreateResponse(post.getId(),post.getPostType());
    }

    @Override
    public CommunityPostDto readCommunityPost(Long postId) throws ChangeSetPersister.NotFoundException {
        return CommunityPostDto.toDto(postJpaRepository.findById(postId).orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    public MarketPostDto readMarketPost(Long postId) throws ChangeSetPersister.NotFoundException {
        return MarketPostDto.toDto(postJpaRepository.findById(postId).orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    @Override
    public RuralExpPostDto readRuralExpPost(Long postId) throws ChangeSetPersister.NotFoundException {
        return RuralExpPostDto.toDto(postJpaRepository.findById(postId).orElseThrow(ChangeSetPersister.NotFoundException::new));
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
