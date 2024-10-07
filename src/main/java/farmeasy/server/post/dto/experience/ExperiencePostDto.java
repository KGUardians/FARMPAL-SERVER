package farmeasy.server.post.dto.experience;

import farmeasy.server.file.dto.ImageDto;
import farmeasy.server.post.domain.CropCategory;
import farmeasy.server.post.domain.exprience.ExperiencePost;
import farmeasy.server.post.domain.exprience.Recruitment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ExperiencePostDto {
    private Long postId;
    private String title;
    private int postLike;
    private CropCategory cropCategory;
    private ExperienceAuthorDto author;
    private int viewCount;
    private List<ImageDto> imageList;
    private Recruitment recruitment;

    public static ExperiencePostDto toDto(ExperiencePost post){
        return ExperiencePostDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .postLike(post.getPostLike())
                .cropCategory(post.getCropCategory())
                .viewCount(post.getViewCount())
                .author(ExperienceAuthorDto.toDto(post.getAuthor()))
                .imageList(post.getImageList().stream()
                        .map(i->ImageDto.toDto(i, i.getId()))
                        .collect(Collectors.toList()))
                .recruitment(post.getRecruitment())
                .build();
    }
}
