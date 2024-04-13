package com.example.farmeasyserver.repository;

import com.example.farmeasyserver.dto.PostImageQueryDto;
import com.example.farmeasyserver.dto.mainpage.MarAndRuralPostDto;
import com.example.farmeasyserver.dto.post.MarketPostDto;
import com.example.farmeasyserver.dto.post.RuralExpPostDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public List<MarAndRuralPostDto> findTopNAllByMarketPostDto(int limit){
        return em.createQuery("select new com.example.farmeasyserver.dto.mainpage.MarAndRuralPostDto(mp.id,mp.title,a.name,mp.like,i.price,i.gram,a.address)"+
                        " from MarketPost mp"+
                        " join mp.author a"+
                        " join mp.item i"+
                        " order by mp.id desc")
                .setMaxResults(limit)
                .getResultList();
    }

    public List<MarAndRuralPostDto> findTopNAllByRuralExpPostDto(int limit){
        return em.createQuery("select new com.example.farmeasyserver.dto.mainpage.MarAndRuralPostDto(rp.id,rp.title,a.name,rp.like,a.address)"+
                        " from RuralExpPost rp"+
                        " join rp.author a"+
                        " order by rp.id desc")
                .setMaxResults(limit)
                .getResultList();
    }

    //
    public List<MarAndRuralPostDto> getMainPageMarket(){
        List<MarAndRuralPostDto> results = findTopNAllByMarketPostDto(4);
        Map<Long, PostImageQueryDto> marketDtoMap = getImageDtoMap(getPostIds(results));
        results.forEach(p -> p.setImage(marketDtoMap.get(p.getPostId())));

        return results;
    }

    public List<MarAndRuralPostDto> getMainPageRural(){
        List<MarAndRuralPostDto> results = findTopNAllByRuralExpPostDto(4);
        Map<Long, PostImageQueryDto> ruralExpDtoMap = getImageDtoMap(getPostIds(results));
        results.forEach(p->p.setImage(ruralExpDtoMap.get(p.getPostId())));

        return results;
    }

    public static List<Long> getPostIds(List<MarAndRuralPostDto> results){
        List<Long> marketIds = results.stream()
                .map(m -> m.getPostId())
                .collect(Collectors.toList());
        return marketIds;
    }

    //게시글에 있는 모든 이미지 매핑
    public Map<Long,List<PostImageQueryDto>> getImageDtosMap(List<Long> postIds){
        List<PostImageQueryDto> postImages = em.createQuery(
                "select new com.example.farmeasyserver.dto.PostImageQueryDto(p.id,i.originName,i.uniqueName)"+
                        " from Image i"+
                        " join i.post p"+
                        " where i.post.id in :postIds",PostImageQueryDto.class)
                .setParameter("postIds",postIds)
                .getResultList();

        Map<Long, List<PostImageQueryDto>> marketDtoMap = postImages.stream()
                .collect(Collectors.groupingBy(PostImageQueryDto::getPostId));

        return marketDtoMap;
    }

    //게시글에 있는 첫번째 이미지만 매핑
    public Map<Long, PostImageQueryDto> getImageDtoMap(List<Long> postIds){
        List<PostImageQueryDto> postImages = em.createQuery(
                        "select new com.example.farmeasyserver.dto.PostImageQueryDto(p.id,i.originName,i.uniqueName)"+
                                " from Image i"+
                                " join i.post p"+
                                " where i.post.id in :postIds",PostImageQueryDto.class)
                .setParameter("postIds",postIds)
                .getResultList();

        Map<Long, PostImageQueryDto> marketDtoMap = postImages.stream()
                .collect(Collectors.toMap(PostImageQueryDto::getPostId, Function.identity(), (existing, replacement) -> existing));

        return marketDtoMap;
    }
}
