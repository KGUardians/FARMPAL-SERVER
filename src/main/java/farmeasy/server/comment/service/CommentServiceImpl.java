package farmeasy.server.comment.service;

import farmeasy.server.comment.domain.Comment;
import farmeasy.server.comment.dto.CommentDto;
import farmeasy.server.comment.dto.CommentRequest;
import farmeasy.server.comment.repository.CommentJpaRepo;
import farmeasy.server.post.domain.community.CommunityPost;
import farmeasy.server.post.repository.community.CommunityJpaRepo;
import farmeasy.server.user.domain.User;
import farmeasy.server.util.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentJpaRepo commentJpaRepo;
    private final CommunityJpaRepo communityJpaRepo;

    @Transactional
    @Override
    public void requestComment(Long postId, CommentRequest req, User author) {
        CommunityPost post = communityJpaRepo.findByIdWithUser(postId)
                .orElseThrow(() -> new ResourceNotFoundException("CommunityPost", "post", null));
        Comment comment = new Comment(req.getComment(),post,author);
        commentJpaRepo.save(comment);
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId, User author) {
        Comment comment = commentJpaRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId+""));
        if (!comment.getAuthor().getId().equals(author.getId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        commentJpaRepo.delete(comment);
    }

    @Transactional
    @Override
    public void updateComment(Long commentId, String updateComment, User author){
        Comment comment = commentJpaRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId+""));
        if (!comment.getAuthor().getId().equals(author.getId())) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        comment.setComment(updateComment);
        commentJpaRepo.save(comment);
    }
}
