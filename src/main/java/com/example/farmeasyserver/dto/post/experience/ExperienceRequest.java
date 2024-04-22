package com.example.farmeasyserver.dto.post.experience;

import com.example.farmeasyserver.entity.board.community.CommunityType;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExperienceRequest {

    private CommunityType communityType = CommunityType.RURAL_EXP;

    @ApiModelProperty(value = "게시글 제목", notes = "게시글 제목을 입력해주세요", required = true, example = "my title")
    @NotBlank(message = "게시글 제목을 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "게시글 본문", notes = "게시글 본문을 입력해주세요", required = true, example = "my content")
    @NotBlank(message = "게시글 본문을 입력해주세요.")
    private String content;

    @ApiModelProperty(hidden = true)
    @Null
    private Long userId;

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

    @ApiModelProperty(value = "이미지", notes = "이미지를 첨부해주세요.")
    private List<MultipartFile> imageList = new ArrayList<>();
}
