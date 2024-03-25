package com.example.farmeasyserver.entity.saleofitems.order;

import com.example.farmeasyserver.entity.saleofitems.delivery.Delivery;
import com.example.farmeasyserver.entity.saleofitems.item.Item;
import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.lang.reflect.Member;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @OneToOne(mappedBy = "order",fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    private Timestamp orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @PrePersist
    protected void onCreate(){
        orderDate = new Timestamp(System.currentTimeMillis());
    }

    public void setCustomer(User customer){
        this.customer = customer;
        customer.getOrders().add(this);
    }
}
