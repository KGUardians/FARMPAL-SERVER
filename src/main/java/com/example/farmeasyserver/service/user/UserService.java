package com.example.farmeasyserver.service.user;

import com.example.farmeasyserver.dto.user.JoinUserForm;
import com.example.farmeasyserver.dto.user.LoginRequest;
import com.example.farmeasyserver.dto.user.UserDto;
import com.example.farmeasyserver.dto.user.UserTokenDto;


public interface UserService {
    UserDto join (JoinUserForm user);

    UserTokenDto signIn(LoginRequest req);

}
