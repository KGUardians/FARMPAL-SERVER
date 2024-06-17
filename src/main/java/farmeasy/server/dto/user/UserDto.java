package farmeasy.server.dto.user;

import farmeasy.server.entity.user.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String name;
    private Address address;
    private String birthday;
}

