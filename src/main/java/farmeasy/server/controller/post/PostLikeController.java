package farmeasy.server.controller.post;

import farmeasy.server.dto.post.PostLikeResponse;
import farmeasy.server.dto.response.Response;
import farmeasy.server.entity.user.User;
import farmeasy.server.service.post.community.PostLikeService;
import farmeasy.server.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;
    private final UserService userService;

    @PutMapping("/{postId}")
    public Response likePost(@PathVariable Long postId) {
        User user = userService.getByUsername();
        return Response.success(new PostLikeResponse(postLikeService.likePost(postId, user)));
    }

    @DeleteMapping("/{postId}")
    public Response unlikePost(@PathVariable Long postId) {
        User user = userService.getByUsername();
        return Response.success(new PostLikeResponse(postLikeService.unlikePost(postId, user)));
    }
}
