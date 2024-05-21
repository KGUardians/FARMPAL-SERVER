package com.example.farmeasyserver.dto.user;

import lombok.Data;

@Data
public class LoginReq {
    private String username;
    private String password;
}
