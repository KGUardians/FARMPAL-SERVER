package com.example.farmeasyserver.repository.post;

import com.example.farmeasyserver.entity.board.market.MarketPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarketRepository extends JpaRepository<MarketPost,Long> {
    @Query("SELECT mp FROM MarketPost mp ORDER BY mp.id DESC")
    List<MarketPost> findTop5OrderByIdDesc();
}
