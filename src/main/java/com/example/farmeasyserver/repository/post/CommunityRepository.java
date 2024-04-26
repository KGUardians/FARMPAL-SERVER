package com.example.farmeasyserver.repository.post;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommunityRepository extends JpaRepository<CommunityPost,Long> {
    @Query("SELECT cp FROM CommunityPost cp ORDER BY cp.id DESC limit 5")
    List<CommunityPost> findTop5OrderByIdDesc();

    @Query("SELECT cp.id FROM CommunityPost cp ORDER BY cp.id DESC limit 5")
    List<Long> findIdOrderByIdDesc();
    @Query("select new com.example.farmeasyserver.dto.ImageDto(i.id,cp.id,i.originName,i.uniqueName)"+
            " from Image i"+
            " join i.c_post cp"+
            " where i.c_post.id in :postIds")
    List<ImageDto> findTop5ImagesDto(List<Long> postIds);
    /*{
        List<ImageDto> postImages = em.createQuery(
                        ,ImageDto.class)
                .setParameter("postIds",postIds)
                .getResultList();

        Map<Long, List<ImageDto>> marketDtoMap = postImages.stream()
                .collect(Collectors.groupingBy(ImageDto::getPostId));

        return marketDtoMap;
    }*/

    @Query("select cp from CommunityPost cp join fetch cp.author where cp.id = :id")
    Optional<CommunityPost> findByIdWithUser(Long id);



}
