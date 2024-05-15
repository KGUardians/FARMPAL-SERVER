package com.example.farmeasyserver.controller;

import com.example.farmeasyserver.dto.user.JoinUserReq;
import com.example.farmeasyserver.dto.response.Response;
import com.example.farmeasyserver.dto.user.LoginReq;
import com.example.farmeasyserver.dto.user.RegisterFarmReq;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public Response joinUser(@RequestBody @Valid JoinUserReq form){
        return Response.success(userService.join(form));
    }

    @PostMapping("/sign-in")
    public Response signIn(@RequestBody LoginReq req){
        return Response.success(userService.signIn(req));
    }

    @PostMapping("/farm/register")
    public Response registerFarm(@RequestBody RegisterFarmReq req, @AuthenticationPrincipal User user){
        return Response.success(userService.createFarm(req, user));
    }

}
