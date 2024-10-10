package farmeasy.server.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class CommentDto {
    private Long id;
    private Long postId;
    private String comment;
    private String author;
    private LocalDateTime postedTime;
    private LocalDateTime updatedTime;

    @Builder
    public CommentDto(Long id, Long postId, String comment, String author, LocalDateTime updatedTime) {
        this.id = id;
        this.postId = postId;
        this.comment = comment;
        this.author = author;
        this.updatedTime = updatedTime;
    }
}
