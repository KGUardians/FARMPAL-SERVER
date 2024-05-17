package com.example.farmeasyserver.dto.post.experience;

import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpApplicationDto {
    private String farmName;
    private String detailedRecruitmentCondition;
    private String experienceDate;
    private String experienceTime;

    public static ExpApplicationDto toDto(ExperiencePost post){
        return new ExpApplicationDto(
                post.getAuthor().getFarm().getFarmName(),
                post.getRecruitment().getDetailedRecruitmentCondition(),
                post.getRecruitment().getStartTime(),
                post.getRecruitment().getStartTime()
        );
    }
}
