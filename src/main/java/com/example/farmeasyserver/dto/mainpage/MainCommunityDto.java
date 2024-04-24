package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.entity.board.community.CommunityPost;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MainCommunityDto {
    private Long postId;
    private String title;
    private int postLike;

    public static MainCommunityDto toDto(CommunityPost communityPost){
        return new MainCommunityDto(
                communityPost.getId(),
                communityPost.getTitle(),
                communityPost.getPostLike()
        );
    }
}
