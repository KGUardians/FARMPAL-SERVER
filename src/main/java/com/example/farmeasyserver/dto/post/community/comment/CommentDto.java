package com.example.farmeasyserver.dto.post.community.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class CommentDto {
    private Long id;
    private String comment;
    private String author;
    private LocalDateTime postedTime;
    private LocalDateTime updatedTime;

}
