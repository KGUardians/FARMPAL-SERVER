package farmeasy.server.dto.post.experience;

import farmeasy.server.dto.mainpage.PostListDto;
import farmeasy.server.entity.board.CropCategory;
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
