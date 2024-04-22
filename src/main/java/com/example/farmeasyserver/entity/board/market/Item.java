package com.example.farmeasyserver.entity.board.market;

import com.example.farmeasyserver.entity.board.ItemCategory;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private MarketPost post;
    private String itemName;
    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;
    @Column(name = "itemPrice")
    private int price;
    private int gram;
    private boolean isAvailable = true;

    public Item(String itemName, ItemCategory itemCategory, int price, int gram){
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.price = price;
        this.gram = gram;
    }
}
