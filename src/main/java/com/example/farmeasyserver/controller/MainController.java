package com.example.farmeasyserver.controller;

import com.example.farmeasyserver.dto.mainpage.MainPageDto;
import com.example.farmeasyserver.dto.response.Response;
import com.example.farmeasyserver.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final PostService postService;
    @GetMapping("/")
    public Response mainPage(){
        MainPageDto mainPageDto = new MainPageDto(
                postService.getMainCommunityPosts(),
                postService.getMainMarketPosts(),
                postService.getMainExperiencePosts());

        return Response.success(mainPageDto);
    }
}
