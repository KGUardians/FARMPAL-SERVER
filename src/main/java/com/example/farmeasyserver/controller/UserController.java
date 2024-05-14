package com.example.farmeasyserver.controller;

import com.example.farmeasyserver.dto.user.JoinUserForm;
import com.example.farmeasyserver.dto.response.Response;
import com.example.farmeasyserver.dto.user.LoginRequest;
import com.example.farmeasyserver.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/join")
    public void joinForm(){

    }

    @PostMapping("/join")
    public Response joinUser(@RequestBody @Valid JoinUserForm form){
        return Response.success(userService.join(form));
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginRequest req){
        return Response.success(userService.signIn(req));
    }
}
