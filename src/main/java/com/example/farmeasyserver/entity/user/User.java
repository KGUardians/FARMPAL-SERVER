package com.example.farmeasyserver.entity.user;

import com.example.farmeasyserver.entity.saleofitems.item.Item;
import com.example.farmeasyserver.entity.saleofitems.order.Order;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
}
