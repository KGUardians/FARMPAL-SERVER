package farmeasy.server.repository.post.experience;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import farmeasy.server.dto.post.experience.ExperienceListDto;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static farmeasy.server.entity.board.exprience.QExperiencePost.experiencePost;

@Repository
public class ExpRepo {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public ExpRepo(EntityManager em){
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }
    public List<ExperienceListDto> findTop4OrderByIdDesc(){
        return em.createQuery("SELECT new farmeasy.server.dto.post.experience.ExperienceListDto(ep.id,ep.postLike,ep.title,a.address.sigungu,ep.cropCategory,a.farm.farmName,ep.recruitment.startTime) " +
                "FROM ExperiencePost ep " +
                "join ep.author a " +
                "ORDER BY ep.id DESC limit 4", ExperienceListDto.class)
                .getResultList();
    }

    public Slice<ExperienceListDto> findPostList(ExpFilter filter, Pageable pageable){

        int pageSize = pageable.getPageSize();
        List<ExperienceListDto> postList;
        postList = query
                .select(Projections.constructor(
                      ExperienceListDto.class,
                        experiencePost.id,
                        experiencePost.postLike,
                        experiencePost.title,
                        experiencePost.author.address.sigungu,
                        experiencePost.cropCategory,
                        experiencePost.author.farm.farmName,
                        experiencePost.recruitment.startTime
                        )
                )
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
