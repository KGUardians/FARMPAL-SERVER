package farmeasy.server.service.user;

import farmeasy.server.dto.TokenDto;
import farmeasy.server.dto.user.*;
import farmeasy.server.entity.user.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;


public interface UserService {
    UserDto join (JoinUserReq user);

    ResponseEntity<String> signIn(LoginReq req, HttpServletResponse response);

    RegisterFarmReq createFarm(RegisterFarmReq req, User user);

    User getByUsername();

    ResponseEntity<String> refreshToken(Cookie[] cookies);

    void checkUser(User user, Long authorId);

}
