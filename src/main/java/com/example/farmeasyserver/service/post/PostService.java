package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.mainpage.ListCommunityDto;
import com.example.farmeasyserver.dto.mainpage.ListExperienceDto;
import com.example.farmeasyserver.dto.mainpage.ListMarketDto;
import com.example.farmeasyserver.dto.post.CreatePostResponse;
import com.example.farmeasyserver.dto.post.community.CommunityPostDto;
import com.example.farmeasyserver.dto.post.market.MarketPostDto;
import com.example.farmeasyserver.dto.post.experience.ExperiencePostDto;
import com.example.farmeasyserver.dto.post.community.CommunityPostRequest;
import com.example.farmeasyserver.dto.post.market.MarketPostRequest;
import com.example.farmeasyserver.dto.post.experience.ExperiencePostRequest;
import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostService {
    List<ListCommunityDto> getMainCommunityPosts();
    List<ListMarketDto> getMainMarketPosts();
    List<ListExperienceDto> getMainExperiencePosts();

    CreatePostResponse createCommunityPost(CommunityPostRequest req) throws ChangeSetPersister.NotFoundException;
    CreatePostResponse createMarketPost(MarketPostRequest req) throws ChangeSetPersister.NotFoundException;
    CreatePostResponse createExperiencePost(ExperiencePostRequest req) throws ChangeSetPersister.NotFoundException;

    CommunityPostDto readCommunityPost(Long postId) throws ChangeSetPersister.NotFoundException;
    MarketPostDto readMarketPost(Long postId) throws ChangeSetPersister.NotFoundException;
    ExperiencePostDto readExperiencePost(Long postId) throws ChangeSetPersister.NotFoundException;


    Slice<ListCommunityDto> getCommunityPostList(CommunityType type, CropCategory crop, String search, Pageable pageable);
    Slice<ListMarketDto> getMarketPostList(CropCategory crop, Pageable pageable);
    Slice<ListExperienceDto> getExperiencePostList(String sido,String sigungu, Pageable pageable);

}
