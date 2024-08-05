package farmeasy.server.controller.post;

import farmeasy.server.entity.user.User;
import farmeasy.server.service.post.community.PostLikeService;
import farmeasy.server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;
    private final UserService userService;

    @PutMapping("/{postId}")
    public ResponseEntity<String> likePost(@PathVariable Long postId) {
        User user = userService.getByUsername();
        return postLikeService.likePost(postId, user);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> unlikePost(@PathVariable Long postId) {
        User user = userService.getByUsername();
        return postLikeService.unlikePost(postId, user);
    }
}
