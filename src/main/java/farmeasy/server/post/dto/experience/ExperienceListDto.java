package farmeasy.server.post.dto.experience;

import farmeasy.server.main.dto.PostListDto;
import farmeasy.server.post.domain.CropCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class ExperienceListDto extends PostListDto {
    private String title;
    private String sigungu;
    private String farmName;
    private String startTime;

    public ExperienceListDto(Long postId, int postLike, String title, String sigungu, CropCategory cropCategory, String farmName, String startTime){
        super(postId,postLike,cropCategory);
        this.title = title;
        this.sigungu = sigungu;
        this.farmName = farmName;
        this.startTime = startTime;
    }
}
