package com.example.farmeasyserver.entity.user;

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

    public Farm(String farmName, Address farmAddress) {
        this.farmName = farmName;
        this.farmAddress = farmAddress;
    }

    public void setUser(User user){
        this.user = user;
        user.setFarm(this);
    }

}
