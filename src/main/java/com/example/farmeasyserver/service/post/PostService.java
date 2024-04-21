package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.mainpage.MainComPostDto;
import com.example.farmeasyserver.dto.mainpage.ImagePostDto;
import com.example.farmeasyserver.dto.post.PostCreateResponse;
import com.example.farmeasyserver.dto.post.community.CommunityPostDto;
import com.example.farmeasyserver.dto.post.market.MarketPostDto;
import com.example.farmeasyserver.dto.post.ruralexp.RuralExpPostDto;
import com.example.farmeasyserver.dto.post.community.CommunityRequest;
import com.example.farmeasyserver.dto.post.market.MarketRequest;
import com.example.farmeasyserver.dto.post.ruralexp.RuralExpRequest;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface PostService {
    List<MainComPostDto> getMainPageComPosts();
    List<ImagePostDto> getMainPageMarketPosts();
    List<ImagePostDto> getMainPageRuralExpPosts();

    PostCreateResponse createCommunityPost(CommunityRequest req) throws ChangeSetPersister.NotFoundException;
    PostCreateResponse createMarketPost(MarketRequest req) throws ChangeSetPersister.NotFoundException;
    PostCreateResponse createRuralExpPost(RuralExpRequest req) throws ChangeSetPersister.NotFoundException;

    CommunityPostDto readCommunityPost(Long postId) throws ChangeSetPersister.NotFoundException;
    MarketPostDto readMarketPost(Long postId) throws ChangeSetPersister.NotFoundException;
    RuralExpPostDto readRuralExpPost(Long postId) throws ChangeSetPersister.NotFoundException;
}
