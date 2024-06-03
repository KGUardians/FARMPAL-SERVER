package farmeasy.server.dto.user;

import farmeasy.server.dto.TokenDto;
import farmeasy.server.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenDto {
    private String username;
    private TokenDto token;

    public static UserTokenDto toDto(User user, TokenDto token) {
        return new UserTokenDto(user.getUsername(), token);
    }
}
