package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ListCommunityDto extends ListPostDto {

    private String title;

    public ListCommunityDto(Long id, String title, int postLike, CropCategory cropCategory) {
        super(id,postLike,cropCategory);
        this.title = title;
    }

    public static ListCommunityDto toDto(CommunityPost communityPost){
        return new ListCommunityDto(
                communityPost.getId(),
                communityPost.getTitle(),
                communityPost.getPostLike(),
                communityPost.getCropCategory()
        );
    }

}
