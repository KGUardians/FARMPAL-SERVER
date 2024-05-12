package com.example.farmeasyserver.service.user;

import com.example.farmeasyserver.dto.user.JoinUserForm;
import com.example.farmeasyserver.dto.user.UserDto;


public interface UserService {
    UserDto join (JoinUserForm user);

}
