package farmeasy.server.post.dto;

import farmeasy.server.post.domain.PostType;
import lombok.Builder;
import lombok.Data;


@Data
public class CreatePostResponse {
    private Long postId;
    private PostType postType;

    @Builder
    public CreatePostResponse(Long postId, PostType postType) {
        this.postId = postId;
        this.postType = postType;
    }
}
