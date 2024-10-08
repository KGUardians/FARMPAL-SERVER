package farmeasy.server.user.controller;

import farmeasy.server.dto.response.Response;
import farmeasy.server.user.dto.JoinUserReq;
import farmeasy.server.user.dto.LoginReq;
import farmeasy.server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    @Operation(summary = "유저 회원가입 요청")
    public Response joinUser(@RequestBody @Valid JoinUserReq form){
        return Response.success(userService.join(form));
    }

    @PostMapping("/sign-in")
    @Operation(summary = "유저 로그인 요청")
    public ResponseEntity<String> signIn(@RequestBody LoginReq req, HttpServletResponse response){
        return userService.signIn(req, response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "refresh token 생성 요청")
    public ResponseEntity<String> requestRefreshToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        return userService.refreshToken(cookies);
    }

}
