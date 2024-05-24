package com.example.farmeasyserver.dto.post.community.comment;

import lombok.Data;

@Data
public class CommentRequest {
    private long authorId;
    private String comment;

}
