package com.example.farmeasyserver.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepository2 {

   /* private final EntityManager em;

    public List<ImagePostDto> findTopNAllByPostDto(int limit, String postType){
        return em.createQuery("select new com.example.farmeasyserver.dto.mainpage.ImagePostDto(p.id,p.title,a.name,p.like,i.price,i.gram,a.address)"+
                        " from Post p"+
                        " join p.author a"+
                        " join p.item i"+
                        " where p.postType in :postType"+
                        " order by p.id desc")
                .setParameter("postType",postType)
                .setMaxResults(limit)
                .getResultList();
    }

    //
    public List<ImagePostDto> getMainPagePost(String postWType){
        List<ImagePostDto> results = findTopNAllByPostDto(4,postType);
        Map<Long, ImageDto> postDtoMap = getImageDtoMap(getPostIds(results));
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
    public Map<Long,List<ImageDto>> getImageDtosMap(List<Long> postIds){
        List<ImageDto> postImages = em.createQuery(
                "select new com.example.farmeasyserver.dto.ImageDto(p.id,i.originName,i.uniqueName)"+
                        " from Image i"+
                        " join i.post p"+
                        " where i.post.id in :postIds",ImageDto.class)
                .setParameter("postIds",postIds)
                .getResultList();

        Map<Long, List<ImageDto>> marketDtoMap = postImages.stream()
                .collect(Collectors.groupingBy(ImageDto::getPostId));

        return marketDtoMap;
    }

    //게시글에 있는 첫번째 이미지만 매핑
    public Map<Long, ImageDto> getImageDtoMap(List<Long> postIds){
        List<ImageDto> postImages = em.createQuery(
                        "select new com.example.farmeasyserver.dto.ImageDto(p.id,i.originName,i.uniqueName)"+
                                " from Image i"+
                                " join i.post p"+
                                " where i.post.id in :postIds",ImageDto.class)
                .setParameter("postIds",postIds)
                .getResultList();

        Map<Long, ImageDto> marketDtoMap = postImages.stream()
                .collect(Collectors.toMap(ImageDto::getPostId, Function.identity(), (existing, replacement) -> existing));

        return marketDtoMap;
    }*/
}
