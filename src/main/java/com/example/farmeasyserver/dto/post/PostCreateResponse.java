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
    private CommunityType communityType;
    private Item item;
    private String farmName;
    private List<Image> imageList;

    public PostCreateResponse(Long postId, CommunityType communityType, List<Image> imageList) {
        this.postId = postId;
        this.communityType = communityType;
        this.imageList = imageList;
    }

    public PostCreateResponse(Long postId, Item item, List<Image> imageList) {
        this.postId = postId;
        this.item = item;
        this.imageList = imageList;
    }

    public PostCreateResponse(Long postId, String farmName, List<Image> imageList) {
        this.postId = postId;
        this.farmName = farmName;
        this.imageList = imageList;
    }
}
