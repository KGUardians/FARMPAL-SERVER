package farmeasy.server.dto.post.experience;

import farmeasy.server.dto.ImageDto;
import farmeasy.server.entity.board.CropCategory;
import farmeasy.server.entity.board.exprience.ExperiencePost;
import farmeasy.server.entity.board.exprience.Recruitment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
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
        return new ExperiencePostDto(
                post.getId(),
                post.getTitle(),
                post.getPostLike(),
                post.getCropCategory(),
                ExperienceAuthorDto.toDto(post.getAuthor()),
                post.getImageList().stream().map(i->ImageDto.toDto(i, i.getId())).collect(Collectors.toList()),
                post.getRecruitment()
        );
    }
}
