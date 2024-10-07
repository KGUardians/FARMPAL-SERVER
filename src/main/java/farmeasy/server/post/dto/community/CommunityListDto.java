package farmeasy.server.dto.post.community;

import farmeasy.server.main.dto.PostListDto;
import farmeasy.server.post.domain.CropCategory;
import farmeasy.server.post.domain.community.CommunityPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
