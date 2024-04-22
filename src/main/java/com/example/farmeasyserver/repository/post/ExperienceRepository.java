package com.example.farmeasyserver.repository.post;

import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<ExperiencePost,Long> {
    @Query("SELECT ep FROM ExperiencePost ep ORDER BY ep.id DESC")
    List<ExperiencePost> findTop5OrderByIdDesc();
}
