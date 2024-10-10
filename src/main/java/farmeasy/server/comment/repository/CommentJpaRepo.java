package farmeasy.server.comment.repository;

import farmeasy.server.comment.domain.Comment;
import farmeasy.server.comment.dto.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentJpaRepo extends JpaRepository<Comment,Long> {

    @Query("select new farmeasy.server.comment.dto.CommentDto(c.id,c.post.id,c.comment,c.author.username,c.postedTime,c.updatedTime) " +
            "from Comment c " +
            "where c.post.id in :postIdList")
    List<CommentDto> findCommentDtoListByPostIds(List<Long> postIdList);
}
