package com.example.farmeasyserver.entity.user;

import com.example.farmeasyserver.dto.user.RegisterFarmReq;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User user;
    private String farmName;
    @Embedded
    private Address farmAddress;


    public Farm(RegisterFarmReq req) {
        this.farmName = req.getFarmName();
        this.farmAddress = req.getFarmAddress();
    }

    public void setUser(User user){
        this.user = user;
        user.setFarm(this);
        user.setRole(Role.FARMER);
    }

}
