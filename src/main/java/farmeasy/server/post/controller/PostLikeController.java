package farmeasy.server.post.controller;

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

    @PutMapping("/{postId}")
    public ResponseEntity<String> likePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal User user
    ) {
        return postLikeService.likePost(postId, user);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> unlikePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal User user
    ) {
        return postLikeService.unlikePost(postId, user);
    }
}
