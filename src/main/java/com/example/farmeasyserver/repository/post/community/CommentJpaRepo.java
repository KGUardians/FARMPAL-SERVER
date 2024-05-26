package com.example.farmeasyserver.repository.post.community;

import com.example.farmeasyserver.dto.post.community.comment.CommentDto;
import com.example.farmeasyserver.entity.board.community.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentJpaRepo extends JpaRepository<Comment,Long> {

    @Query("select new com.example.farmeasyserver.dto.post.community.comment.CommentDto(c.id,c.post.id,c.comment,c.author.username,c.postedTime,c.updatedTime) " +
            "from Comment c " +
            "where c.post.id in :postIdList")
    List<CommentDto> findCommentDtoListByPostIds(List<Long> postIdList);
}
