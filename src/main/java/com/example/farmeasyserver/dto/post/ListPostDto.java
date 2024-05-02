package com.example.farmeasyserver.dto.post;

import com.example.farmeasyserver.entity.board.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ListPostDto {
    private Long postId;
    private String title;
    private int postLike;

    public abstract ListPostDto toDto(Post post);
}
