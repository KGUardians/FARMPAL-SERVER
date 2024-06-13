package farmeasy.server.dto.post.experience;

import farmeasy.server.dto.ImageDto;
import farmeasy.server.entity.board.CropCategory;
import farmeasy.server.entity.board.exprience.ExperiencePost;
import farmeasy.server.entity.board.exprience.Recruitment;
import lombok.*;

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
    private List<ImageDto> imageList;
    private Recruitment recruitment;

    public static ExperiencePostDto toDto(ExperiencePost post){
        return ExperiencePostDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .postLike(post.getPostLike())
                .cropCategory(post.getCropCategory())
                .author(ExperienceAuthorDto.toDto(post.getAuthor()))
                .imageList(post.getImageList().stream()
                        .map(i->ImageDto.toDto(i, i.getId()))
                        .collect(Collectors.toList()))
                .recruitment(post.getRecruitment())
                .build();
    }
}
