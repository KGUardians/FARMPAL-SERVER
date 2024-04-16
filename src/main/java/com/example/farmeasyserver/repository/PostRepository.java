package com.example.farmeasyserver.repository;

import com.example.farmeasyserver.dto.PostImageQueryDto;
import com.example.farmeasyserver.dto.mainpage.ImagePostDto;
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

    public List<ImagePostDto> findTopNAllByPostDto(int limit, String postType){
        return em.createQuery("select new com.example.farmeasyserver.dto.mainpage.ImagePostDto(p.id,p.title,a.name,p.like,i.price,i.gram,a.address)"+
                        " from Post p"+
                        " join p.author a"+
                        " join p.item i"+
                        " where p.postType = :postType"+
                        " order by p.id desc")
                .setParameter("postType",postType)
                .setMaxResults(limit)
                .getResultList();
    }

    //
    public List<ImagePostDto> getMainPagePost(String postType){
        List<ImagePostDto> results = findTopNAllByPostDto(4,postType);
        Map<Long, PostImageQueryDto> postDtoMap = getImageDtoMap(getPostIds(results));
        results.forEach(p -> p.setImage(postDtoMap.get(p.getPostId())));

        return results;
    }

    public static List<Long> getPostIds(List<ImagePostDto> results){
        List<Long> postIds = results.stream()
                .map(ImagePostDto::getPostId)
                .collect(Collectors.toList());
        return postIds;
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
