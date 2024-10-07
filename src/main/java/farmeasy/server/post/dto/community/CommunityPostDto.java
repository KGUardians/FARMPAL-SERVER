package farmeasy.server.post.dto.community;

import farmeasy.server.file.dto.ImageDto;
import farmeasy.server.post.domain.CropCategory;
import farmeasy.server.post.domain.community.CommunityPost;
import farmeasy.server.post.domain.community.CommunityType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class CommunityPostDto {
    private Long postId;
    private CommunityAuthorDto author;
    private String title;
    private int postLike;
    private CommunityType communityType;
    private CropCategory cropCategory;
    private String content;
    private int viewCount;
    private List<ImageDto> imageList;

    public static CommunityPostDto toDto(CommunityPost post){
        return CommunityPostDto.builder()
                .postId(post.getId())
                .author(CommunityAuthorDto.toDto(post.getAuthor()))
                .title(post.getTitle())
                .postLike(post.getPostLike())
                .communityType(post.getCommunityType())
                .cropCategory(post.getCropCategory())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .imageList(post.getImageList().stream()
                        .map(i->ImageDto.toDto(i, i.getId()))
                        .collect(Collectors.toList()))
                .build();
    }
}
