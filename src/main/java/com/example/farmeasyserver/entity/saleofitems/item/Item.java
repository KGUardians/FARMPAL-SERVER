package com.example.farmeasyserver.entity.saleofitems.item;

import com.example.farmeasyserver.entity.saleofitems.order.Order;
import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;
    @Column(name = "itemName")
    private String Name;
    @Column(name = "itemPrice")
    private double price;
    private String imagePath;
    private boolean isAvailable;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public void setSeller(User seller){
        this.seller = seller;
        seller.getItems().add(this);
    }
}
