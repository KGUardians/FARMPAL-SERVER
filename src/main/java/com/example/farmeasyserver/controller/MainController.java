package com.example.farmeasyserver.controller;

import com.example.farmeasyserver.dto.mainpage.MainPageDto;
import com.example.farmeasyserver.dto.response.Response;
import com.example.farmeasyserver.service.post.CommunityPostService;
import com.example.farmeasyserver.service.post.ExperiencePostService;
import com.example.farmeasyserver.service.post.MarketPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final CommunityPostService communityPostService;
    private final MarketPostService marketPostService;
    private final ExperiencePostService experiencePostService;

    @GetMapping
    public Response mainPage(){
        MainPageDto mainPageDto = new MainPageDto(
                communityPostService.getRecentCommunityPostDtos(),
                marketPostService.getRecentMarketPostDtos(),
                experiencePostService.getRecentExperiencePostDtos());

        return Response.success(mainPageDto);
    }
}
