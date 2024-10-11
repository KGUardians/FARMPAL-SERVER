package farmeasy.server.post.dto.experience;

import farmeasy.server.post.domain.exprience.ExperiencePost;
import farmeasy.server.post.domain.exprience.Recruitment;
import farmeasy.server.post.dto.CreatePostRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateExpPostRequest extends CreatePostRequest {

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

    private static Recruitment reqToRecruitment(CreateExpPostRequest req){
        return Recruitment.builder()
                .startDate(req.getStartDate())
                .startTime(req.getStartTime())
                .totalCapacity(req.getRecruitmentNum())
                .remainingCapacity(req.getRecruitmentNum())
                .recruitmentConditions(req.getDetailedRecruitmentCondition())
                .build();
    }
    public static ExperiencePost toEntity(CreateExpPostRequest req){
        return new ExperiencePost(
            CreateExpPostRequest.reqToRecruitment(req)
        );
    }

}
