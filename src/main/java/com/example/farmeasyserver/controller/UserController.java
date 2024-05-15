package com.example.farmeasyserver.controller;

import com.example.farmeasyserver.dto.user.JoinUserForm;
import com.example.farmeasyserver.dto.response.Response;
import com.example.farmeasyserver.dto.user.LoginRequest;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.PostType;
import com.example.farmeasyserver.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public Response joinUser(@RequestBody @Valid JoinUserForm form){
        return Response.success(userService.join(form));
    }

    @PostMapping("/sign-in")
    public Response signIn(@RequestBody LoginRequest req){
        return Response.success(userService.signIn(req));
    }

}
