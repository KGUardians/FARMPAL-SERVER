package com.example.farmeasyserver.repository;

import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import com.example.farmeasyserver.entity.board.ruralexp.RuralExpPost;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    //메인 페이지 조회
    public <T> List<T> findTopNOrderByPostedTime(int limit, Class<T> entityType) {
        String jpql;
        if (entityType.equals(MarketPost.class)) {
            jpql = "select mp from MarketPost mp join fetch mp.author a join fetch mp.item ORDER BY mp.postedTime";
        } else if (entityType.equals(RuralExpPost.class)) {
            jpql = "select rp from RuralExpPost rp join fetch rp.author a ORDER BY rp.postedTime";
        } else {
            throw new IllegalArgumentException("Unsupported entity type: " + entityType);
        }

        return em.createQuery(jpql, entityType)
                .setMaxResults(limit)
                .getResultList();
    }


}
