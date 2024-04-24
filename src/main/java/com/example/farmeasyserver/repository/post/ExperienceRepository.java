package com.example.farmeasyserver.repository.post;

import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<ExperiencePost,Long> {
    @Query("SELECT ep FROM ExperiencePost ep join fetch ep.author ORDER BY ep.id DESC")
    List<ExperiencePost> findTop5OrderByIdDesc();

    @Query("select ep from ExperiencePost ep join fetch ep.author where ep.id = :id")
    Optional<ExperiencePost> findByIdWithUser(Long id);

}
