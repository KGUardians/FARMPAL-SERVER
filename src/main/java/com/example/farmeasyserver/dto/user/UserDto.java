package com.example.farmeasyserver.dto.user;

import com.example.farmeasyserver.entity.user.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String name;
    private Address address;
    private String birthday;
}

