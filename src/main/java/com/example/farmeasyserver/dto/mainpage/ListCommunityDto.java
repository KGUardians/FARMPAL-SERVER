package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.dto.post.ListPostDto;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ListCommunityDto extends ListPostDto {

    public ListCommunityDto(Long id, String title, int postLike) {
        super(id,title,postLike);
    }

    public static ListCommunityDto toDto(CommunityPost communityPost){
        return new ListCommunityDto(
                communityPost.getId(),
                communityPost.getTitle(),
                communityPost.getPostLike()
        );
    }

    @Override
    public ListCommunityDto toDto(Post post) {
        CommunityPost communityPost = (CommunityPost) post;
         return new ListCommunityDto(
                 communityPost.getId(),
                 communityPost.getTitle(),
                 communityPost.getPostLike()
         );
    }
}
