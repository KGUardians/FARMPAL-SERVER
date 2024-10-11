package farmeasy.server.post.dto.experience.expapplication;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ExpApplicationRequest {
    @NotNull(message = "신청 인원 수는 필수 값입니다.")
    @Min(value = 1, message = "신청 인원 수는 1명 이상이어야 합니다.")
    private int participants;
}
