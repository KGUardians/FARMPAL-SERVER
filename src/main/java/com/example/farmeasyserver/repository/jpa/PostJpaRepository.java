package com.example.farmeasyserver.repository.jpa;

import com.example.farmeasyserver.entity.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<Post,Long> {
    List<Post> findTop5ByPostTypeOrderByIdDesc(String postType);
}
