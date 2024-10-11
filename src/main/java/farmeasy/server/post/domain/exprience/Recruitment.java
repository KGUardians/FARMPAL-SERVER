package farmeasy.server.post.domain.exprience;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Recruitment {

    private String startDate;
    private String startTime;
    private int totalCapacity;  // 총 모집 인원
    private int remainingCapacity;  // 남은 인원

    @Lob
    private String recruitmentConditions;  // 상세 모집 조건

    // 모집 인원 감소
    public void reduceCapacity(int participants) {
        if (participants > remainingCapacity) {
            throw new IllegalArgumentException("남은 인원이 부족합니다.");
        }
        remainingCapacity -= participants;
    }

    // 모집 인원 증가 (취소 시 사용)
    public void increaseCapacity(int participants) {
        remainingCapacity += participants;
    }

}
