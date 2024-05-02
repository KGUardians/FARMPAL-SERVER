package com.example.farmeasyserver.dto.post.experience;

import com.example.farmeasyserver.dto.post.PostCreateRequest;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExperienceRequest extends PostCreateRequest {

    //private CommunityType communityType = CommunityType.RURAL_EXP;

    @ApiModelProperty(value = "게시글 본문", notes = "게시글 본문을 입력해주세요", required = true, example = "my content")
    @NotBlank(message = "게시글 본문을 입력해주세요.")
    private String content;

    @ApiModelProperty(value = "시작 시간", notes = "시작 시간을 입력해주세요", required = true, example = "my content")
    @NotBlank(message = "시간을 입력해주세요.")
    private LocalDateTime startTime;
    @ApiModelProperty(value = "모집 인원", notes = "인원 수 입력해주세요", required = true, example = "my content")
    @NotBlank(message = "게시글 본문을 입력해주세요.")
    private int recruitmentNum;

    @ApiModelProperty(value = "상세 모집 조건", notes = "상세 모집 조건을 입력해주세요", required = true, example = "my content")
    @NotBlank(message = "상세 모집 조건을 입력해주세요.")
    @Lob
    private String detailedRecruitmentCondition;

}
