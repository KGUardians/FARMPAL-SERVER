package com.example.farmeasyserver.service.post.community;

import com.example.farmeasyserver.dto.post.CreatePostResponse;
import com.example.farmeasyserver.dto.post.community.CommunityListDto;
import com.example.farmeasyserver.dto.post.community.CommunityPostDto;
import com.example.farmeasyserver.dto.post.community.CreateCommPostRequest;
import com.example.farmeasyserver.dto.post.community.UpdateCommPostReq;
import com.example.farmeasyserver.dto.post.community.comment.CommentRequest;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.community.CommunityFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunityPostService {

    List<CommunityListDto> getRecentCommunityPostDtos();

    CreatePostResponse createCommunityPost(CreateCommPostRequest req, User author);

    Long deleteCommunityPost(Long postId, User user);

    CommunityPostDto readCommunityPost(Long postId);

    CommunityPostDto updateCommunityPost(Long postId, UpdateCommPostReq req, User user);

    Slice<CommunityListDto> getCommunityPosts(CommunityFilter filter, Pageable pageable);

    CommentRequest requestComment(Long postId, CommentRequest req, User user);
}
