package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.post.community.CommentRequest;
import com.example.farmeasyserver.dto.post.community.ListCommunityDto;
import com.example.farmeasyserver.dto.post.experience.ListExperienceDto;
import com.example.farmeasyserver.dto.post.market.ListMarketDto;
import com.example.farmeasyserver.dto.post.CreatePostResponse;
import com.example.farmeasyserver.dto.post.community.CommunityPostDto;
import com.example.farmeasyserver.dto.post.experience.ExpApplicationRequest;
import com.example.farmeasyserver.dto.post.market.MarketPostDto;
import com.example.farmeasyserver.dto.post.experience.ExperiencePostDto;
import com.example.farmeasyserver.dto.post.community.CommunityPostRequest;
import com.example.farmeasyserver.dto.post.market.MarketPostRequest;
import com.example.farmeasyserver.dto.post.experience.ExperiencePostRequest;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.community.CommunityFilter;
import com.example.farmeasyserver.repository.post.experience.ExpFilter;
import com.example.farmeasyserver.repository.post.market.MarketFilter;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostService {
    List<ListCommunityDto> getMainCommunityPosts();
    List<ListMarketDto> getMainMarketPosts();
    List<ListExperienceDto> getMainExperiencePosts();

    CreatePostResponse createCommunityPost(CommunityPostRequest req, CommunityType type, User author);
    CreatePostResponse createMarketPost(MarketPostRequest req,User author);
    CreatePostResponse createExperiencePost(ExperiencePostRequest req, User author);

    CommunityPostDto readCommunityPost(Long postId) throws ChangeSetPersister.NotFoundException;
    MarketPostDto readMarketPost(Long postId) throws ChangeSetPersister.NotFoundException;
    ExperiencePostDto readExperiencePost(Long postId) throws ChangeSetPersister.NotFoundException;


    Slice<ListCommunityDto> getCommunityPostList(CommunityFilter filter, Pageable pageable);
    Slice<ListMarketDto> getMarketPostList(MarketFilter filter, Pageable pageable);
    Slice<ListExperienceDto> getExperiencePostList(ExpFilter filter, Pageable pageable);

    ExpApplicationRequest requestExperience(ExpApplicationRequest req) throws Exception;

    CommentRequest requestComment(Long postId, CommentRequest req) throws ChangeSetPersister.NotFoundException;
}
