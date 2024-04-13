package com.example.farmeasyserver.dto.post;

import com.example.farmeasyserver.dto.PostImageQueryDto;
import lombok.Data;

import java.util.List;

@Data
public abstract class PostDto {

    private Long postId;
    private String title;
    private int like;
    private String author;
    private String content;
    private List<PostImageQueryDto> images;

}
