package com.example.farmeasyserver.dto.post;

import com.example.farmeasyserver.entity.board.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PostCreateResponse {
    private Long postId;
    private PostType postType;
}
