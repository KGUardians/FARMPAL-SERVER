package com.example.farmeasyserver.repository.post.experience;

import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<ExperiencePost,Long> {

    @Query("select ep from ExperiencePost ep " +
            "join fetch ep.author " +
            "where ep.id = :id")
    Optional<ExperiencePost> findByIdWithUser(Long id);

}
