package farmeasy.server.dto.post.experience.expapplication;

import farmeasy.server.post.domain.exprience.ExperiencePost;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExpApplicationPageDto {
    private String farmName;
    private String detailedRecruitmentCondition;
    private String experienceDate;
    private String experienceTime;

    public static ExpApplicationPageDto toDto(ExperiencePost post){
        return ExpApplicationPageDto.builder()
                .farmName(post.getAuthor().getFarm().getFarmName())
                .detailedRecruitmentCondition(post.getRecruitment()
                        .getDetailedRecruitmentCondition())
                .experienceDate(post.getRecruitment().getStartDate())
                .experienceTime(post.getRecruitment().getStartTime())
                .build();
    }
}
