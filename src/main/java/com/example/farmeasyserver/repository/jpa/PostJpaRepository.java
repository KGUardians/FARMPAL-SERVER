package com.example.farmeasyserver.repository.jpa;

import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostJpaRepository extends JpaRepository<Post,Long> {
    List<CommunityPost> findTop5ByOrderByIdDesc();
}
