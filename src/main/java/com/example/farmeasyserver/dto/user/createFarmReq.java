package com.example.farmeasyserver.dto.user;

import com.example.farmeasyserver.entity.user.Address;
import lombok.Data;

@Data
public class createFarmReq {
    private Long userId;
    private String farmName;
    private Address farmAddress;
}
