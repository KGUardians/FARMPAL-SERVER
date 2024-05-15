package com.example.farmeasyserver.dto.user;

import com.example.farmeasyserver.entity.user.Address;
import lombok.Data;

@Data
public class FarmDto {
    private String farmName;
    private Address address;
}
