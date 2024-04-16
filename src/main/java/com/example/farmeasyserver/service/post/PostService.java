package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.mainpage.MainComPostDto;
import com.example.farmeasyserver.dto.mainpage.ImagePostDto;
import com.example.farmeasyserver.dto.post.community.CommunityRequest;
import com.example.farmeasyserver.dto.post.community.CommunityResponse;
import com.example.farmeasyserver.dto.post.market.MarketRequest;
import com.example.farmeasyserver.dto.post.market.MarketResponse;
import com.example.farmeasyserver.dto.post.ruralexp.RuralExpRequest;
import com.example.farmeasyserver.dto.post.ruralexp.RuralExpResponse;

import java.util.List;

public interface PostService {
    List<MainComPostDto> getMainPageComPosts();
    List<ImagePostDto> getMainPageMarketPosts();
    List<ImagePostDto> getMainPageRuralExpPosts();

    CommunityResponse createCommunityPost(CommunityRequest req);
    MarketResponse createMarketPost(MarketRequest req);
    RuralExpResponse createRuralExpPost(RuralExpRequest req);
}
