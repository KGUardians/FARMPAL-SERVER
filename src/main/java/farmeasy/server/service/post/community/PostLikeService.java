package farmeasy.server.service.post.community;

import farmeasy.server.entity.board.Likes;
import farmeasy.server.entity.board.Post;
import farmeasy.server.entity.user.User;
import farmeasy.server.repository.post.LikesRepository;
import farmeasy.server.util.PostUtil;
import farmeasy.server.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final LikesRepository likesRepository;
    private final PostUtil postUtil;

    public Likes likePost(Long postId, User user) {
        Post post = postUtil.findPost(postId);
        post.increaseLikes();
        return likesRepository.save(new Likes(user, post));
    }

    public Likes unlikePost(Long postId, User user) {
        Post post = postUtil.findPost(postId);
        Likes like = likesRepository.findByPostIdAndUserId(postId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Like", "Like Id", "Post Id : " + postId + "User Id : " + user.getId()));
        post.decreaseLikes();
        return like;
    }
}
