package com.example.farmeasyserver.dto.mainpage;

import java.util.List;

public class MainPageDto {
    List<MainCommunityDto> mainCommunityDtoList;
    List<MainMarketDto> mainMarketDtoList;
    List<MainExperienceDto> mainExperienceDtoList;

    public MainPageDto(List<MainCommunityDto> mainCommunityPosts, List<MainMarketDto> mainMarketPosts, List<MainExperienceDto> mainExperiencePosts) {
        this.mainCommunityDtoList = mainCommunityPosts;
        this.mainMarketDtoList = mainMarketPosts;
        this.mainExperienceDtoList = mainExperiencePosts;
    }
}
