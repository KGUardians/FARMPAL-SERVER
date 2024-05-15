package com.example.farmeasyserver.dto.user;

import com.example.farmeasyserver.entity.user.Address;
import com.example.farmeasyserver.entity.user.Farm;
import lombok.Data;

@Data
public class RegisterFarmReq {
    private Long userId;
    private String farmName;
    private Address farmAddress;

    public static Farm toEntity(RegisterFarmReq req){
        return new Farm(req.getFarmName(),req.farmAddress);
    }
}
