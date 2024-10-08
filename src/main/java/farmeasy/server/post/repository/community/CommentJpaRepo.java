package farmeasy.server.post.repository.community;

import farmeasy.server.post.domain.community.Comment;
import farmeasy.server.post.dto.community.comment.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentJpaRepo extends JpaRepository<Comment,Long> {

    @Query("select new farmeasy.server.post.dto.community.comment.CommentDto(c.id,c.post.id,c.comment,c.author.username,c.postedTime,c.updatedTime) " +
            "from Comment c " +
            "where c.post.id in :postIdList")
    List<CommentDto> findCommentDtoListByPostIds(List<Long> postIdList);
}
