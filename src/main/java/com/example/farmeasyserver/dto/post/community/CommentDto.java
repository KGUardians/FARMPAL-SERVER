package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.entity.board.community.Comment;
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

    public static CommentDto toDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getComment(),
                comment.getAuthor().getName(),
                comment.getPostedTime(),
                comment.getUpdatedTime()
        );
    }
}
