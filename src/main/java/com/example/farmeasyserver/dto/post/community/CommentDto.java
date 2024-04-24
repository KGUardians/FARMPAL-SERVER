package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.entity.board.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class CommentDto {
    private Long id;
    private String comment;
    private CommunityAuthorDto authorDto;
    private LocalDateTime postedTime;

    public static CommentDto toDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getComment(),
                CommunityAuthorDto.toDto(comment.getAuthor()),
                comment.getPostedTime()
        );
    }
}
