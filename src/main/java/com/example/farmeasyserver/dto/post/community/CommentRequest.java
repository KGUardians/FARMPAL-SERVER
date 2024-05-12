package com.example.farmeasyserver.dto.post.community;

import lombok.Data;

@Data
public class CommentRequest {
    private long authorId;
    private String comment;

}
