package com.example.farmeasyserver.entity.user;

import com.example.farmeasyserver.dto.user.RegisterFarmReq;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "farm")
    private User user;
    private String farmName;
    @Embedded
    private Address farmAddress;

    private Address reqToAddress(RegisterFarmReq req){
        return new Address(
                req.getZipcode(),
                req.getAddress(),
                req.getSido(),
                req.getSigungu()
        );
    }

    public Farm(RegisterFarmReq req) {
        this.farmName = req.getFarmName();
        this.farmAddress = reqToAddress(req);
    }

    public void setUser(User user){
        this.user = user;
        user.setFarm(this);
        user.setRole(Role.FARMER);
    }

}
