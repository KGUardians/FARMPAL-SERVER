package farmeasy.server.user.dto;

import farmeasy.server.user.domain.Address;
import farmeasy.server.user.domain.Farm;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FarmDto {
    private String farmName;
    private Address address;

    public static FarmDto toDto(Farm farm){
        return new FarmDto(farm.getFarmName(), farm.getFarmAddress());
    }
}
