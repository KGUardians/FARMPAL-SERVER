package com.example.farmeasyserver.repository.post;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.entity.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostJpaRepo extends JpaRepository<Post,Long> {
    @Query("select new com.example.farmeasyserver.dto.ImageDto(i.id,i.post.id,i.originName,i.uniqueName)"+
            " from Image i"+
            " where i.post.id in :postIds")
    List<ImageDto> findImagesDtoByPostIds(List<Long> postIds);

}
