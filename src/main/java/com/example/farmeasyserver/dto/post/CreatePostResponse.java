package com.example.farmeasyserver.dto.post;

import com.example.farmeasyserver.entity.board.PostType;
import lombok.Data;


@Data
public class CreatePostResponse {
    private Long postId;
    private PostType postType;

    public CreatePostResponse(Long postId, PostType postType) {
        this.postId = postId;
        this.postType = postType;
    }
}
