package farmeasy.server.comment.controller;

import farmeasy.server.comment.dto.CommentRequest;
import farmeasy.server.comment.service.CommentService;
import farmeasy.server.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    @Operation(summary = "커뮤니티 게시글 댓글 작성")
    public ResponseEntity<CommentRequest> comment(
            @PathVariable Long postId,
            @RequestBody CommentRequest req,
            @AuthenticationPrincipal User author
    ) {
        commentService.requestComment(postId, req, author);
        return ResponseEntity.ok(req);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal User author
    ) {
        commentService.deleteComment(commentId, author);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{commentId}")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long commentId,
            @RequestBody String updateComment,
            @AuthenticationPrincipal User author
    ) {
        commentService.updateComment(commentId, updateComment, author);
        return ResponseEntity.noContent().build();
    }
}
