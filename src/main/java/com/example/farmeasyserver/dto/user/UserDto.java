package com.example.farmeasyserver.dto.user;

import com.example.farmeasyserver.entity.user.Address;
import com.example.farmeasyserver.entity.user.Day;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String name;
    private Address address;
    private Day birthday;
}
