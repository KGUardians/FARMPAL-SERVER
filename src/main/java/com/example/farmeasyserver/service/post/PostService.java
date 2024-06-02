package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.post.community.*;
import com.example.farmeasyserver.dto.post.community.comment.CommentRequest;
import com.example.farmeasyserver.dto.post.experience.*;
import com.example.farmeasyserver.dto.post.experience.expapplication.ExpApplicationPageDto;
import com.example.farmeasyserver.dto.post.experience.expapplication.ExpApplicationRequest;
import com.example.farmeasyserver.dto.post.market.MarketListDto;
import com.example.farmeasyserver.dto.post.CreatePostResponse;
import com.example.farmeasyserver.dto.post.market.MarketPostDto;
import com.example.farmeasyserver.dto.post.market.CreateMktPostRequest;
import com.example.farmeasyserver.dto.post.market.UpdateMktPostReq;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.community.CommunityFilter;
import com.example.farmeasyserver.repository.post.experience.ExpFilter;
import com.example.farmeasyserver.repository.post.market.MarketFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostService {
    List<CommunityListDto> getRecentCommunityPostDtos();
    List<MarketListDto> getRecentMarketPostDtos();
    List<ExperienceListDto> getRecentExperiencePostDtos();

    CreatePostResponse createCommunityPost(CreateCommPostRequest req, User author);
    CreatePostResponse createMarketPost(CreateMktPostRequest req, User user);
    CreatePostResponse createExperiencePost(CreateExpPostRequest req, User user);

    Long deleteCommunityPost(Long postId, User user);
    Long deleteMarketPost(Long postId, User user);
    Long deleteExperiencePost(Long postId, User user);

    CommunityPostDto readCommunityPost(Long postId);
    MarketPostDto readMarketPost(Long postId);
    ExperiencePostDto readExperiencePost(Long postId);

    CommunityPostDto updateCommunityPost(Long postId, UpdateCommPostReq req, User user);
    ExperiencePostDto updateExperiencePost(Long postId, UpdateExpPostReq req, User user);
    MarketPostDto updateMarketPost(Long postId, UpdateMktPostReq req, User user);


    Slice<CommunityListDto> getCommunityPostList(CommunityFilter filter, Pageable pageable);
    Slice<MarketListDto> getMarketPostList(MarketFilter filter, Pageable pageable);
    Slice<ExperienceListDto> getExperiencePostList(ExpFilter filter, Pageable pageable);

    ExpApplicationPageDto getExpAppPage(Long postId);
    ExpApplicationRequest requestExpApp(Long postId, ExpApplicationRequest req, User applicants) throws Exception;

    CommentRequest requestComment(Long postId, CommentRequest req, User user);

}
