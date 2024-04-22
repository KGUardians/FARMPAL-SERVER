package com.example.farmeasyserver.repository.post;

import com.example.farmeasyserver.entity.board.community.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityRepository extends JpaRepository<CommunityPost,Long> {
    @Query("SELECT cp FROM CommunityPost cp ORDER BY cp.id DESC")
    List<CommunityPost> findTop5OrderByIdDesc();
}
