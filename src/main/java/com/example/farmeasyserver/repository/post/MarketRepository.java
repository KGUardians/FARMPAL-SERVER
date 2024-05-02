package com.example.farmeasyserver.repository.post;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MarketRepository extends JpaRepository<MarketPost,Long> {
    @Query("SELECT mp FROM MarketPost mp join fetch mp.author ORDER BY mp.id DESC limit 4")
    List<MarketPost> findTop4OrderByIdDesc();

    @Query("select mp from MarketPost mp join fetch mp.author where mp.id = :id")
    Optional<MarketPost> findByIdWithUser(Long id);

    @Query("select new com.example.farmeasyserver.dto.ImageDto(i.id,mp.id,i.originName,i.uniqueName)"+
            " from Image i"+
            " join i.m_post mp"+
            " where i.m_post.id in :postIds")
    List<ImageDto> findImagesDtoByPostIds(List<Long> postIds);

}
