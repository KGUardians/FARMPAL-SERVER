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

    public Recruitment(UpdateExpPostReq req){
        this.startDate = req.getStartDate();
        this.startTime = req.getStartTime();
        this.recruitmentNum = req.getRecruitmentNum();
        this.detailedRecruitmentCondition = req.getDetailedRecruitmentCondition();
    }
}
