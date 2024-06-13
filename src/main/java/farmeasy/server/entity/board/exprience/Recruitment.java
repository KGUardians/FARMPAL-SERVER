package farmeasy.server.entity.board.exprience;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recruitment {
    private String startDate;
    private String startTime;
    private int recruitmentNum;
    @Lob
    private String detailedRecruitmentCondition;

}
