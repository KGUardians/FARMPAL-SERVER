package com.example.farmeasyserver.entity.user;

import jakarta.persistence.*;

@Entity
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;

    private String farmName;
    @Embedded
    private Address address;

}
