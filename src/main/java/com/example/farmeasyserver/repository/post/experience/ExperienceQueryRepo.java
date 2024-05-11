package com.example.farmeasyserver.repository.post.experience;

import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.farmeasyserver.entity.board.exprience.QExperiencePost.*;

@Repository
public class ExperienceQueryRepo {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public ExperienceQueryRepo(EntityManager em){
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Slice<ExperiencePost> findPostList(ExperienceFilter filter, Pageable pageable){

        int pageSize = pageable.getPageSize();
        List<ExperiencePost> postList;
        postList = query
                .select(experiencePost)
                .from(experiencePost)
                .innerJoin(experiencePost.author)
                .where(sidoEq(filter.getSido()),sigunguEq(filter.getSigungu()))
                .orderBy(experiencePost.postedTime.desc())
                .offset(pageable.getOffset())
                .limit(pageSize+1)
                .fetch();

        boolean hasNext = postList.size() > pageSize;
        if(hasNext){
            postList.remove(pageSize);
        }
        return new SliceImpl<>(postList,pageable,hasNext);
    }


    private BooleanExpression sidoEq(String sido){
        return (sido != null && !sido.isEmpty()) ? experiencePost.author.address.sido.eq(sido):null;
    }

    private BooleanExpression sigunguEq(String sigungu){
        return (sigungu != null && !sigungu.isEmpty()) ? experiencePost.author.address.sigungu.eq(sigungu):null;
    }
}
