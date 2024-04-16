package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.mainpage.MainComPostDto;
import com.example.farmeasyserver.dto.mainpage.ImagePostDto;
import com.example.farmeasyserver.dto.post.community.CommunityRequest;
import com.example.farmeasyserver.dto.post.community.CommunityResponse;
import com.example.farmeasyserver.dto.post.market.MarketRequest;
import com.example.farmeasyserver.dto.post.market.MarketResponse;
import com.example.farmeasyserver.dto.post.ruralexp.RuralExpRequest;
import com.example.farmeasyserver.dto.post.ruralexp.RuralExpResponse;
import com.example.farmeasyserver.entity.board.Image;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.repository.PostRepository;
import com.example.farmeasyserver.repository.jpa.PostJpaRepository;
import com.example.farmeasyserver.repository.jpa.UserJpaRepository;
import com.example.farmeasyserver.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
    public CommunityResponse createCommunityPost(CommunityRequest req) {
        Post post = postJpaRepository.save(
                CommunityRequest.CommunityToEntity(
                        req,
                        userJpaRepository
                )
        );
        uploadImages(post.getImageList(),req.getImageList());
        return null;
    }

    @Override
    public MarketResponse createMarketPost(MarketRequest req) {
        Post post = postJpaRepository.save(
                MarketRequest.MarketToEntity(
                        req,
                        userJpaRepository
                )
        );
        uploadImages(post.getImageList(),req.getImageList());
        return null;
    }

    @Override
    public RuralExpResponse createRuralExpPost(RuralExpRequest req) {
        Post post = postJpaRepository.save(
                RuralExpRequest.RuralExpToEntity(
                        req,
                        userJpaRepository
                )
        );
        uploadImages(post.getImageList(),req.getImageList());
        return null;
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
