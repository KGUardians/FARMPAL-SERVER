package farmeasy.server.farm.dto;

import farmeasy.server.farm.domain.Farm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RegisterFarmReq {
    @Schema(description = "농장 이름", example = "민준 농장")
    private String farmName;
    @Schema(description = "우편번호")
    private Long zipcode;
    @Schema(description = "주소", example = "충청북도 청주시 상당구 월평로 189")
    private String address;
    @Schema(description = "도", example = "충청북도")
    private String sido;
    @Schema(description = "시구", example = "청주시 상당구")
    private String sigungu;


    public static Farm toEntity(RegisterFarmReq req){
        return new Farm(req);
    }
}
