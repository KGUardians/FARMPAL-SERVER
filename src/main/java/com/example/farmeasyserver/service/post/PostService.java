package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.mainpage.MainComPostDto;
import com.example.farmeasyserver.dto.mainpage.MarAndRuralPostDto;

import java.util.List;

public interface PostService {
    List<MainComPostDto> getMainPageComPosts();
    List<MarAndRuralPostDto> getMainPageMarketPosts();
    List<MarAndRuralPostDto> getMainPageRuralExpPosts();
}
