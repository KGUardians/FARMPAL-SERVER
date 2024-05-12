package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.dto.mainpage.ListPostDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ListCommunityDto extends ListPostDto {

    private String title;
    private int commentCount;

    public ListCommunityDto(Long id, String title, int postLike, CropCategory cropCategory,int commentCount) {
        super(id,postLike,cropCategory);
        this.title = title;
        this.commentCount = commentCount;
    }

    public static ListCommunityDto toDto(CommunityPost communityPost){
        return new ListCommunityDto(
                communityPost.getId(),
                communityPost.getTitle(),
                communityPost.getPostLike(),
                communityPost.getCropCategory(),
                communityPost.getCommentList().size()
        );
    }

}
