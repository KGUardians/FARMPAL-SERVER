package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.mainpage.MainCommunityDto;
import com.example.farmeasyserver.dto.mainpage.MainExperienceDto;
import com.example.farmeasyserver.dto.mainpage.MainMarketDto;
import com.example.farmeasyserver.dto.post.PostCreateResponse;
import com.example.farmeasyserver.dto.post.community.CommunityPostDto;
import com.example.farmeasyserver.dto.post.market.MarketPostDto;
import com.example.farmeasyserver.dto.post.experience.ExperiencePostDto;
import com.example.farmeasyserver.dto.post.community.CommunityRequest;
import com.example.farmeasyserver.dto.post.market.MarketRequest;
import com.example.farmeasyserver.dto.post.experience.ExperienceRequest;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface PostService {
    List<MainCommunityDto> getMainCommunityPosts();
    List<MainMarketDto> getMainMarketPosts();
    List<MainExperienceDto> getMainExperiencePosts();

    PostCreateResponse createCommunityPost(CommunityRequest req) throws ChangeSetPersister.NotFoundException;
    PostCreateResponse createMarketPost(MarketRequest req) throws ChangeSetPersister.NotFoundException;
    PostCreateResponse createExperiencePost(ExperienceRequest req) throws ChangeSetPersister.NotFoundException;

    CommunityPostDto readCommunityPost(Long postId) throws ChangeSetPersister.NotFoundException;
    MarketPostDto readMarketPost(Long postId) throws ChangeSetPersister.NotFoundException;
    ExperiencePostDto readExperiencePost(Long postId) throws ChangeSetPersister.NotFoundException;
}
