package farmeasy.server.dto.post.community;

import farmeasy.server.entity.board.CropCategory;
import farmeasy.server.entity.board.community.CommunityPost;
import farmeasy.server.dto.mainpage.PostListDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommunityListDto extends PostListDto {

    private String title;
    private int commentCount;

    public CommunityListDto(Long id, String title, int postLike, CropCategory cropCategory) {
        super(id,postLike,cropCategory);
        this.title = title;
    }

    public static CommunityListDto toDto(CommunityPost communityPost){
        return new CommunityListDto(
                communityPost.getId(),
                communityPost.getTitle(),
                communityPost.getPostLike(),
                communityPost.getCropCategory()
        );
    }

}
