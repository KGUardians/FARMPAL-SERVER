package com.example.farmeasyserver.dto.post.experience;

import com.example.farmeasyserver.dto.post.CreatePostRequest;
import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import com.example.farmeasyserver.entity.board.exprience.Recruitment;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExperiencePostRequest extends CreatePostRequest {

    @Schema(description = "시작 날짜", example = "2024-05-24")
    @NotNull
    private String startDate;
    @Schema(description = "시작 시간", example = "오후 7시")
    @NotNull
    private String startTime;
    @Schema(description = "모집 인원", example = "my content")
    @NotNull
    private int recruitmentNum;
    @Schema(description = "상세 모집 조건", example = "my content")
    @NotNull(message = "상세 모집 조건을 입력해주세요.")
    @Lob
    private String detailedRecruitmentCondition;
    private static Recruitment reqToRecruitment(ExperiencePostRequest req){
        return new Recruitment(
                req.getStartDate(),
                req.getStartTime(),
                req.getRecruitmentNum(),
                req.getDetailedRecruitmentCondition()
        );
    }
    public static ExperiencePost toEntity(ExperiencePostRequest req){
        return new ExperiencePost(
            ExperiencePostRequest.reqToRecruitment(req)
        );
    }

}
