package com.example.farmeasyserver.service.user;

import com.example.farmeasyserver.dto.user.JoinUserForm;
import com.example.farmeasyserver.dto.user.UserDto;
import com.example.farmeasyserver.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.Optional;


public interface UserService {
    UserDto join (JoinUserForm user);

}
