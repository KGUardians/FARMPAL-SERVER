package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.mainpage.CommunityDto;
import com.example.farmeasyserver.dto.mainpage.MarketDto;
import com.example.farmeasyserver.dto.mainpage.RuralExpDto;

import java.awt.print.Pageable;
import java.util.List;

public interface PostService {
    List<CommunityDto> getMainPageComPosts();
    List<MarketDto> getMainPageMarketPosts();
    List<RuralExpDto> getMainPageRuralExpPosts();
}
