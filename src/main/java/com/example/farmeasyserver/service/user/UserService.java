package com.example.farmeasyserver.service.user;

import com.example.farmeasyserver.dto.user.JoinUserReq;
import com.example.farmeasyserver.dto.user.LoginReq;
import com.example.farmeasyserver.dto.user.UserDto;
import com.example.farmeasyserver.dto.user.UserTokenDto;


public interface UserService {
    UserDto join (JoinUserReq user);

    UserTokenDto signIn(LoginReq req);

}
