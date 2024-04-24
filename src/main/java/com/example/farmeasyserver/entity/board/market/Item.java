package com.example.farmeasyserver.entity.board.market;

import com.example.farmeasyserver.entity.board.ItemCategory;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Embeddable
@NoArgsConstructor
public class Item {
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
