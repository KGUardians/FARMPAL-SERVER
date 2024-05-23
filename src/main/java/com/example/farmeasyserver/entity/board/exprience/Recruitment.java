package com.example.farmeasyserver.entity.board.exprience;

import com.example.farmeasyserver.dto.post.experience.UpdateExpPostReq;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Recruitment {
    private String startDate;
    private String startTime;
    private int recruitmentNum;
    @Lob
    private String detailedRecruitmentCondition;

    public static Recruitment toEntity(UpdateExpPostReq req){
        return new Recruitment(
                req.getStartDate(),
                req.getStartTime(),
                req.getRecruitmentNum(),
                req.getDetailedRecruitmentCondition()
        );
    }
}
