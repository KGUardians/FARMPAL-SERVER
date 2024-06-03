package farmeasy.server.dto.post.experience;

import farmeasy.server.entity.board.CropCategory;
import farmeasy.server.dto.mainpage.PostListDto;
import lombok.AllArgsConstructor;
import lombok.Data;



@Data
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
