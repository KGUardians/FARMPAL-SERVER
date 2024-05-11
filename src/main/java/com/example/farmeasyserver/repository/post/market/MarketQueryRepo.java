package com.example.farmeasyserver.repository.post.market;

import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.farmeasyserver.entity.board.market.QMarketPost.*;

@Repository
public class MarketQueryRepo {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public MarketQueryRepo(EntityManager em){
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Slice<MarketPost> findPostList(MarketFilter filter, Pageable pageable){

        int pageSize = pageable.getPageSize();
        List<MarketPost> postList;
        postList = query
                .select(marketPost)
                .from(marketPost)
                .innerJoin(marketPost.author)
                .where(cropEq(filter.getCrop()))
                .orderBy(marketPost.postedTime.desc())
                .offset(pageable.getOffset())
                .limit(pageSize+1)
                .fetch();

        boolean hasNext = postList.size() > pageSize;
        if(hasNext){
            postList.remove(pageSize);
        }
        return new SliceImpl<>(postList,pageable,hasNext);
    }

    private BooleanExpression cropEq(CropCategory crop){
        return crop != null && !crop.toString().isEmpty() ? marketPost.cropCategory.eq(crop):null;
    }

}
