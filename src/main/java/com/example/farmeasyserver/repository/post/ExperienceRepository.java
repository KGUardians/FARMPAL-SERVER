package com.example.farmeasyserver.repository.post;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<ExperiencePost,Long> {
    @Query("SELECT ep FROM ExperiencePost ep join fetch ep.author ORDER BY ep.id DESC limit 4")
    List<ExperiencePost> findTop4OrderByIdDesc();
    @Query("SELECT cp.id FROM ExperiencePost cp ORDER BY cp.id DESC limit 4")
    List<Long> findTop4IdOrderByIdDesc();

    @Query("select ep from ExperiencePost ep join fetch ep.author where ep.id = :id")
    Optional<ExperiencePost> findByIdWithUser(Long id);

    @Query("select new com.example.farmeasyserver.dto.ImageDto(i.id,ep.id,i.originName,i.uniqueName)"+
            " from Image i"+
            " join i.e_post ep"+
            " where i.e_post.id in :postIds")
    List<ImageDto> findImagesDtoByPostIds(List<Long> postIds);

}
