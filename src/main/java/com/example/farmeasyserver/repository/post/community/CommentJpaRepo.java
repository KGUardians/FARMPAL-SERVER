package com.example.farmeasyserver.repository.post.community;

import com.example.farmeasyserver.entity.board.community.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepo extends JpaRepository<Comment,Long> {
}