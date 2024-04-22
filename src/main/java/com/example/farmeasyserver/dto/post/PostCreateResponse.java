package com.example.farmeasyserver.dto.post;

import com.example.farmeasyserver.entity.board.community.CommunityType;
import com.example.farmeasyserver.entity.board.market.Item;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class PostCreateResponse {
    private Long postId;
    private CommunityType communityType;
    private Item item;
    private String farmName;

    public PostCreateResponse(Long postId, CommunityType communityType) {
        this.postId = postId;
        this.communityType = communityType;
    }

    public PostCreateResponse(Long postId, Item item) {
        this.postId = postId;
        this.item = item;
    }

    public PostCreateResponse(Long postId, String farmName) {
        this.postId = postId;
        this.farmName = farmName;
    }
}
