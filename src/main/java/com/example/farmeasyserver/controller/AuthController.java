package com.example.farmeasyserver.controller;

import com.example.farmeasyserver.config.login.jwt.JwtProperties;
import com.example.farmeasyserver.dto.user.JoinUserReq;
import com.example.farmeasyserver.dto.response.Response;
import com.example.farmeasyserver.dto.user.LoginReq;
import com.example.farmeasyserver.dto.user.RegisterFarmReq;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
        User user = userService.findByUsername();
        return Response.success(userService.createFarm(req, user));
    }

    @PostMapping("/refresh")
    @Operation(summary = "refresh token 생성 요청")
    public Response requestRefreshToken(HttpServletRequest request){
        String refreshToken = jwtProperties.extractTokenFromHeader(request);
        return Response.success(userService.refreshToken(refreshToken));
    }

}
