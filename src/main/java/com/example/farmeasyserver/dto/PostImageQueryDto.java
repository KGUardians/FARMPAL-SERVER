package com.example.farmeasyserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostImageQueryDto {
    private Long postId;
    private String originName;
    private String uniqueName;
}
