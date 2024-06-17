package farmeasy.server.dto.post;

import farmeasy.server.entity.board.PostType;
import lombok.Data;


@Data
public class CreatePostResponse {
    private Long postId;
    private PostType postType;

    public CreatePostResponse(Long postId, PostType postType) {
        this.postId = postId;
        this.postType = postType;
    }
}
