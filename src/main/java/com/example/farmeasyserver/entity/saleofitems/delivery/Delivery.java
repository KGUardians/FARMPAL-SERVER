package com.example.farmeasyserver.entity.saleofitems.delivery;

import com.example.farmeasyserver.entity.saleofitems.order.Order;
import com.example.farmeasyserver.entity.user.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private Order order;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
