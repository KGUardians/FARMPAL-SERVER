package com.example.farmeasyserver.entity.board.market.item;

import com.example.farmeasyserver.entity.board.market.MarketPost;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private MarketPost marketPost;
    @Column(name = "itemName")
    private String name;
    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;
    @Column(name = "itemPrice")
    private double price;
    private String imagePath;
    private boolean isAvailable;
}
