package com.example.farmeasyserver.dto.post.experience;

import com.example.farmeasyserver.dto.post.UpdatePostRequest;
import com.example.farmeasyserver.entity.board.exprience.Recruitment;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class UpdateExpPostReq extends UpdatePostRequest {
    private String startDate;
    private String startTime;
    private int recruitmentNum;
    @Lob
    private String detailedRecruitmentCondition;

    public static Recruitment reqToRecruitment(UpdateExpPostReq req){
        return new Recruitment(
                req.getStartDate(),
                req.getStartTime(),
                req.getRecruitmentNum(),
                req.getDetailedRecruitmentCondition()
        );
    }
}
