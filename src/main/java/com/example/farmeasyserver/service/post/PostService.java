package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.mainpage.ListCommunityDto;
import com.example.farmeasyserver.dto.mainpage.ListExperienceDto;
import com.example.farmeasyserver.dto.mainpage.ListMarketDto;
import com.example.farmeasyserver.dto.post.PostCreateResponse;
import com.example.farmeasyserver.dto.post.community.CommunityPostDto;
import com.example.farmeasyserver.dto.post.market.MarketPostDto;
import com.example.farmeasyserver.dto.post.experience.ExperiencePostDto;
import com.example.farmeasyserver.dto.post.community.CommunityRequest;
import com.example.farmeasyserver.dto.post.market.MarketRequest;
import com.example.farmeasyserver.dto.post.experience.ExperienceRequest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public interface PostService {
    List<ListCommunityDto> getMainCommunityPosts();
    List<ListMarketDto> getMainMarketPosts();
    List<ListExperienceDto> getMainExperiencePosts();

    PostCreateResponse createCommunityPost(CommunityRequest req) throws ChangeSetPersister.NotFoundException;
    PostCreateResponse createMarketPost(MarketRequest req) throws ChangeSetPersister.NotFoundException;
    PostCreateResponse createExperiencePost(ExperienceRequest req) throws ChangeSetPersister.NotFoundException;

    CommunityPostDto readCommunityPost(Long postId) throws ChangeSetPersister.NotFoundException;
    MarketPostDto readMarketPost(Long postId) throws ChangeSetPersister.NotFoundException;
    ExperiencePostDto readExperiencePost(Long postId) throws ChangeSetPersister.NotFoundException;


    Slice<ListCommunityDto> getCommunityPostList(Pageable pageable);
    Slice<ListMarketDto> getMarketPostList(Pageable pageable,String sido,String sigungu);
    Slice<ListExperienceDto> getExperiencePostList(Pageable pageable,String sido,String sigungu);

}
