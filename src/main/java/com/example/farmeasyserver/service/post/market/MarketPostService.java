package com.example.farmeasyserver.service.post.market;

import com.example.farmeasyserver.dto.post.CreatePostResponse;
import com.example.farmeasyserver.dto.post.market.CreateMktPostRequest;
import com.example.farmeasyserver.dto.post.market.MarketListDto;
import com.example.farmeasyserver.dto.post.market.MarketPostDto;
import com.example.farmeasyserver.dto.post.market.UpdateMktPostReq;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.market.MarketFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface MarketPostService {
    List<MarketListDto> getRecentMarketPostDtos();

    CreatePostResponse createMarketPost(CreateMktPostRequest req, User user);

    MarketPostDto readMarketPost(Long postId);

    MarketPostDto updateMarketPost(Long postId, UpdateMktPostReq req, User user);

    Long deleteMarketPost(Long postId, User author);

    Slice<MarketListDto> getMarketPosts(MarketFilter filter, Pageable pageable);
}
