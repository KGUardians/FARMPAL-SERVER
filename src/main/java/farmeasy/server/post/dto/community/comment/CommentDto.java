package farmeasy.server.dto.post.community.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

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

}
