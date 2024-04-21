package com.example.farmeasyserver.entity.board;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Recruitment {
    private LocalDateTime startTime;
    private int recruitmentNum;
    @Lob
    private String detailedRecruitmentCondition;
}
