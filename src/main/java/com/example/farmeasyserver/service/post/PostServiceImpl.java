package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.mainpage.MainComPostDto;
import com.example.farmeasyserver.dto.mainpage.MarAndRuralPostDto;
import com.example.farmeasyserver.dto.post.CommunityPostDto;
import com.example.farmeasyserver.dto.post.MarketPostDto;
import com.example.farmeasyserver.dto.post.RuralExpPostDto;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.repository.PostRepository;
import com.example.farmeasyserver.repository.jpa.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostJpaRepository postJpaRepository;
    private final PostRepository postRepository;
    @Override
    public List<MainComPostDto> getMainPageComPosts() {
        List<MainComPostDto> communityDtos = new ArrayList<>();
        List<CommunityPost> communityPosts = postJpaRepository.findTop5ByOrderByIdDesc();

        ModelMapper modelMapper = new ModelMapper();
        for(CommunityPost post : communityPosts){
            MainComPostDto dto = modelMapper.map(post, MainComPostDto.class);
            communityDtos.add(dto);
        }

        return communityDtos;
    }

    @Override
    public List<MarAndRuralPostDto> getMainPageMarketPosts() {
        return postRepository.getMainPageMarket();
    }

    @Override
    public List<MarAndRuralPostDto> getMainPageRuralExpPosts() {
        return postRepository.getMainPageRural();
    }
}
