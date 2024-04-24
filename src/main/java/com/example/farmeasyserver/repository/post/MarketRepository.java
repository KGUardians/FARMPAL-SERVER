package com.example.farmeasyserver.repository.post;

import com.example.farmeasyserver.entity.board.market.MarketPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MarketRepository extends JpaRepository<MarketPost,Long> {
    @Query("SELECT mp FROM MarketPost mp join fetch mp.author ORDER BY mp.id DESC")
    List<MarketPost> findTop5OrderByIdDesc();

    @Query("select mp from MarketPost mp join fetch mp.author where mp.id = :id")
    Optional<MarketPost> findByIdWithUser(Long id);

}
