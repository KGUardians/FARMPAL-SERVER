package farmeasy.server.dto.user;

import farmeasy.server.entity.user.Address;
import farmeasy.server.entity.user.Farm;
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
