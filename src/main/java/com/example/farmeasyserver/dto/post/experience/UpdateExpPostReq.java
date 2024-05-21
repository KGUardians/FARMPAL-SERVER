package com.example.farmeasyserver.dto.post.experience;

import com.example.farmeasyserver.dto.post.UpdatePostRequest;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class UpdateExpPostReq extends UpdatePostRequest {
    private String startDate;
    private String startTime;
    private int recruitmentNum;
    @Lob
    private String detailedRecruitmentCondition;
}
