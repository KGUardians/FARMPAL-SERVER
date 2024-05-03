package com.example.farmeasyserver.dto.post.experience;

import com.example.farmeasyserver.dto.post.CreatePostRequest;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExperiencePostRequest extends CreatePostRequest {

    @ApiModelProperty(value = "시작 시간", notes = "시작 시간을 입력해주세요", required = true, example = "my content")
    @NotNull
    private String startTime;
    @ApiModelProperty(value = "모집 인원", notes = "인원 수 입력해주세요", required = true, example = "my content")
    @NotNull
    private int recruitmentNum;
    private String farmName;
    @ApiModelProperty(value = "상세 모집 조건", notes = "상세 모집 조건을 입력해주세요", required = true, example = "my content")
    @NotNull(message = "상세 모집 조건을 입력해주세요.")
    @Lob
    private String detailedRecruitmentCondition;

}
