package com.example.farmeasyserver.controller;

import com.example.farmeasyserver.dto.mainpage.MainPageDto;
import com.example.farmeasyserver.service.post.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final PostServiceImpl postService;
    @GetMapping("/")
    public MainPageDto mainPage(){
        MainPageDto mainPageDto = new MainPageDto(
                postService.getMainPageComPosts(),
                postService.getMainPageMarketPosts(),
                postService.getMainPageRuralExpPosts());

        return mainPageDto;
    }
}
