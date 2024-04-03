package com.example.farmeasyserver.repository.jpa;

import com.example.farmeasyserver.entity.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post,Long> {
}
