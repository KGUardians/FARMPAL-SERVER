package farmeasy.server.user.service;

import farmeasy.server.user.domain.User;
import farmeasy.server.user.dto.JoinUserReq;
import farmeasy.server.user.dto.LoginReq;
import farmeasy.server.user.dto.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;


public interface UserService {
    UserDto join (JoinUserReq user);

    ResponseEntity<String> signIn(LoginReq req, HttpServletResponse response);

    ResponseEntity<String> refreshToken(Cookie[] cookies, User user);

    void checkUser(User user, Long authorId);

}
