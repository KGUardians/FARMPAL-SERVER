package com.example.farmeasyserver.dto.post;

import com.example.farmeasyserver.entity.board.Image;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import com.example.farmeasyserver.entity.board.market.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
public class PostCreateResponse {
    private Long postId;
    private String postType;

    public PostCreateResponse(Long postId, String postType) {
        this.postId = postId;
        this.postType = postType;
    }
}
