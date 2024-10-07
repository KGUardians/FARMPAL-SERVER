package farmeasy.server.dto.post.community.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "커뮤니티 게시판 댓글 작성 dto")
@Data
public class CommentRequest {
    @Schema(description = "댓글 내용")
    private String comment;

}
