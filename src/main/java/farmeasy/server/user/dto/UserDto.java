package farmeasy.server.user.dto;

import farmeasy.server.user.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String name;
    private Address address;
    private String birthday;
}

