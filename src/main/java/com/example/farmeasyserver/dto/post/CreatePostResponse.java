package com.example.farmeasyserver.dto.post;

import lombok.Data;


@Data
public class CreatePostResponse {
    private Long postId;
    private String postType;

    public CreatePostResponse(Long postId, String postType) {
        this.postId = postId;
        this.postType = postType;
    }
}
