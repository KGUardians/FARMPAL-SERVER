package farmeasy.server.post.service.community;

import farmeasy.server.post.domain.Likes;
import farmeasy.server.post.domain.Post;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.repository.LikesRepository;
import farmeasy.server.util.PostUtil;
import farmeasy.server.util.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final LikesRepository likesRepository;

    @Transactional
    public ResponseEntity<String> likePost(Post post, User user) {
        Long postId = post.getId();
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
    public ResponseEntity<String> unlikePost(Post post, User user) {
        Long id = post.getId();
        Likes like = likesRepository.findByPostIdAndUserId(id, user.getId())
                .orElseThrow(() -> new IllegalStateException("User has not liked this post"));

        post.decreaseLikes();
        post.getLikes().remove(like);

        return ResponseEntity.ok("postId : " + id + ", postLikes : " + post.getPostLike() + ", Successfully unliked the post");
    }
}
