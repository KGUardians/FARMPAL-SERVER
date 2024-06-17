package farmeasy.server.service.user;

import farmeasy.server.dto.TokenDto;
import farmeasy.server.dto.user.*;
import farmeasy.server.entity.user.User;


public interface UserService {
    UserDto join (JoinUserReq user);

    UserTokenDto signIn(LoginReq req);

    RegisterFarmReq createFarm(RegisterFarmReq req, User user);

    User getByUsername();

    TokenDto refreshToken(String refreshToken);

    void checkUser(User user, Long authorId);

}
