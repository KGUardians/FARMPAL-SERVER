package farmeasy.server.service.post.community;

import farmeasy.server.dto.response.Response;
import farmeasy.server.entity.board.Likes;
import farmeasy.server.entity.board.Post;
import farmeasy.server.entity.user.User;
import farmeasy.server.repository.post.LikesRepository;
import farmeasy.server.util.PostUtil;
import farmeasy.server.util.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final LikesRepository likesRepository;
    private final PostUtil postUtil;

    @Transactional
    public ResponseEntity<String> likePost(Long postId, User user) {
        Post post = postUtil.findPost(postId);

        Optional<Likes> existingLike = likesRepository.findByPostIdAndUserId(postId, user.getId());
        if (existingLike.isPresent()) {
            throw new IllegalStateException("User has already liked this post");
        }

        post.increaseLikes();
        Likes like = new Likes(user, post);
        post.getLikes().add(like);
        likesRepository.save(like);

        return ResponseEntity.ok("postId : " + postId + ", postLikes : " + post.getPostLike() + ", Successfully liked the post");
    }

    @Transactional
    public ResponseEntity<String> unlikePost(Long postId, User user) {
        Post post = postUtil.findPost(postId);

        Likes like = likesRepository.findByPostIdAndUserId(postId, user.getId())
                .orElseThrow(() -> new IllegalStateException("User has not liked this post"));
        
        post.decreaseLikes();
        post.getLikes().remove(like);

        return ResponseEntity.ok("postId : " + postId + ", postLikes : " + post.getPostLike() + ", Successfully unliked the post");
    }
}
