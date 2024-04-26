package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MainCommunityDto {
    private Long postId;
    private String title;
    private int postLike;
    private ImageDto imageDto;

    public MainCommunityDto(Long postId, String title, int postLike){
        this.postId = postId;
        this.title = title;
        this.postLike = postLike;
    }

    public static MainCommunityDto toDto(CommunityPost communityPost){
        return new MainCommunityDto(
                communityPost.getId(),
                communityPost.getTitle(),
                communityPost.getPostLike()
        );
    }
}
