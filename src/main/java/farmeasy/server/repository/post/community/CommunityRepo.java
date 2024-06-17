package farmeasy.server.repository.post.community;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import farmeasy.server.dto.post.community.CommunityListDto;
import farmeasy.server.entity.board.CropCategory;
import farmeasy.server.entity.board.community.CommunityType;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static farmeasy.server.entity.board.community.QCommunityPost.communityPost;

@Repository
public class CommunityRepo {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public CommunityRepo(EntityManager em){
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }
    public Slice<CommunityListDto> findPostList(CommunityFilter filter, Pageable pageable){

        int pageSize = pageable.getPageSize();
        List<CommunityListDto> postList;
        postList = query
                .select(Projections.constructor(
                        CommunityListDto.class,
                        communityPost.id,
                        communityPost.title,
                        communityPost.postLike,
                        communityPost.cropCategory
                        )
                )
                .from(communityPost)
                .where(typeEq(filter.getType()),
                        cropEq(filter.getCrop()),
                        tSearchEq(filter.getSearch()),
                        cSearchEq(filter.getSearch())
                )
                .orderBy(communityPost.postedTime.desc())
                .offset(pageable.getOffset())
                .limit(pageSize+1)
                .fetch();

        boolean hasNext = postList.size() > pageSize;
        if(hasNext){
            postList.remove(pageSize);
        }
        return new SliceImpl<>(postList,pageable,hasNext);
    }

    private BooleanExpression typeEq(CommunityType type){
        return type != null && !type.toString().isEmpty() ? communityPost.communityType.eq(type):null;
    }

    private BooleanExpression cropEq(CropCategory crop){
        return crop != null && !crop.toString().isEmpty() ? communityPost.cropCategory.eq(crop):null;
    }

    private BooleanExpression tSearchEq(String search){
        return search != null && !search.isEmpty() ? communityPost.title.eq(search):null;
    }

    private BooleanExpression cSearchEq(String search){
        return search != null && !search.isEmpty() ? communityPost.content.eq(search):null;
    }

}
