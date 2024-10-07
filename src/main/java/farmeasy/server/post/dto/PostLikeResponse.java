package farmeasy.server.post.dto;

import farmeasy.server.post.domain.Likes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLikeResponse {
    private Long postId;
    private Long userId;
    private int likeCount;

    public PostLikeResponse(Likes like) {
        this.postId = like.getPost().getId();
        this.userId = like.getUser().getId();
        this.likeCount = like.getPost().getPostLike();
    }
}
