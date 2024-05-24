package com.example.farmeasyserver.entity.board.exprience;

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

}
