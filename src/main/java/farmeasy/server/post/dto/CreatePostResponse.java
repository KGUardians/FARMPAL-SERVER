package farmeasy.server.post.dto;

import farmeasy.server.post.domain.PostType;
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
