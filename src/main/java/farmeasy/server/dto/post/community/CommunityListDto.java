package farmeasy.server.dto.post.community;

import farmeasy.server.entity.board.CropCategory;
import farmeasy.server.entity.board.community.CommunityPost;
import farmeasy.server.dto.mainpage.PostListDto;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class CommunityListDto extends PostListDto {

    private String title;
    private int commentCount;

    @Builder
    public CommunityListDto(Long id, String title, int postLike, CropCategory cropCategory) {
        super(id,postLike,cropCategory);
        this.title = title;
    }

    public static CommunityListDto toDto(CommunityPost communityPost){
        return CommunityListDto.builder()
                .id(communityPost.getId())
                .title(communityPost.getTitle())
                .postLike(communityPost.getPostLike())
                .cropCategory(communityPost.getCropCategory())
                .build();
    }

}
