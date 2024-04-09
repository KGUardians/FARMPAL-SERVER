package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.mainpage.CommunityDto;
import com.example.farmeasyserver.dto.mainpage.MarketDto;
import com.example.farmeasyserver.dto.mainpage.RuralExpDto;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import com.example.farmeasyserver.entity.board.ruralexp.RuralExpPost;
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
    public List<CommunityDto> getMainPageComPosts() {
        List<CommunityDto> communityDtos = new ArrayList<>();
        List<CommunityPost> communityPosts = postJpaRepository.findTop5ByOrderByPostedTimeDesc();

        ModelMapper modelMapper = new ModelMapper();
        for(CommunityPost post : communityPosts){
            CommunityDto dto = modelMapper.map(post, CommunityDto.class);
            communityDtos.add(dto);
        }

        return communityDtos;
    }

    @Override
    public List<MarketDto> getMainPageMarketPosts() {
        List<MarketDto> marketDtos = new ArrayList<>();
        List<MarketPost> marketPosts = postRepository.findTopNOrderByPostedTime(4, MarketPost.class);

        ModelMapper modelMapper = new ModelMapper();
        for(MarketPost post : marketPosts){
            MarketDto dto = modelMapper.map(post, MarketDto.class);
            marketDtos.add(dto);
        }

        return marketDtos;
    }

    @Override
    public List<RuralExpDto> getMainPageRuralExpPosts() {
        List<RuralExpDto> ruralExpDtos = new ArrayList<>();
        List<RuralExpPost> ruralExpPosts = postRepository.findTopNOrderByPostedTime(4, RuralExpPost.class);

        ModelMapper modelMapper = new ModelMapper();
        for(RuralExpPost post : ruralExpPosts){
            RuralExpDto dto = modelMapper.map(post, RuralExpDto.class);
            ruralExpDtos.add(dto);
        }

        return ruralExpDtos;
    }
}
