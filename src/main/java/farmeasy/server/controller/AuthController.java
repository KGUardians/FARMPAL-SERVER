package farmeasy.server.controller;

import farmeasy.server.config.login.jwt.JwtProperties;
import farmeasy.server.dto.response.Response;
import farmeasy.server.dto.user.JoinUserReq;
import farmeasy.server.dto.user.LoginReq;
import farmeasy.server.dto.user.RegisterFarmReq;
import farmeasy.server.entity.user.User;
import farmeasy.server.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtProperties jwtProperties;

    @PostMapping("/sign-up")
    @Operation(summary = "유저 회원가입 요청")
    public Response joinUser(@RequestBody @Valid JoinUserReq form){
        return Response.success(userService.join(form));
    }

    @PostMapping("/sign-in")
    @Operation(summary = "유저 로그인 요청")
    public Response signIn(@RequestBody LoginReq req){
        return Response.success(userService.signIn(req));
    }

    @PostMapping("/farm")
    @Operation(summary = "농장 등록 요청")
    public Response registerFarm(@RequestBody RegisterFarmReq req){
        User user = userService.getByUsername();
        return Response.success(userService.createFarm(req, user));
    }

    @PostMapping("/refresh")
    @Operation(summary = "refresh token 생성 요청")
    public Response requestRefreshToken(HttpServletRequest request){
        String refreshToken = jwtProperties.extractTokenFromHeader(request);
        return Response.success(userService.refreshToken(refreshToken));
    }

}
