package farmeasy.server.post.controller;

import farmeasy.server.post.domain.Post;
import farmeasy.server.post.service.PostService;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.service.community.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;
    private final PostService postService;

    @PutMapping("/{id}")
    public ResponseEntity<String> likePost(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        Post post = postService.findPostById(id);
        return postLikeService.likePost(post, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> unlikePost(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        Post post = postService.findPostById(id);
        return postLikeService.unlikePost(post, user);
    }
}
