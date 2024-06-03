package farmeasy.server.dto.post.experience.expapplication;

import farmeasy.server.entity.board.exprience.ExperiencePost;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpApplicationPageDto {
    private String farmName;
    private String detailedRecruitmentCondition;
    private String experienceDate;
    private String experienceTime;

    public static ExpApplicationPageDto toDto(ExperiencePost post){
        return new ExpApplicationPageDto(
                post.getAuthor().getFarm().getFarmName(),
                post.getRecruitment().getDetailedRecruitmentCondition(),
                post.getRecruitment().getStartTime(),
                post.getRecruitment().getStartTime()
        );
    }
}
