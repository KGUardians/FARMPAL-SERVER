package farmeasy.server.comment.service;

import farmeasy.server.comment.dto.CommentRequest;
import farmeasy.server.user.domain.User;

public interface CommentService {
    void requestComment(Long postId, CommentRequest req, User author);
    void deleteComment(Long commentId, User author);
    void updateComment(Long commentId, String comment, User author);
}
