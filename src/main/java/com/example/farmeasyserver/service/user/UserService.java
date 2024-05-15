package com.example.farmeasyserver.service.user;

import com.example.farmeasyserver.dto.user.*;


public interface UserService {
    UserDto join (JoinUserReq user);

    UserTokenDto signIn(LoginReq req);

    RegisterFarmReq createFarm(RegisterFarmReq req);

}
