package com.example.farmeasyserver.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ListPostDto {
    private Long postId;
    private int postLike;
}
