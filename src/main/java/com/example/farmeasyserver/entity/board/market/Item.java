package com.example.farmeasyserver.entity.board.market;

import com.example.farmeasyserver.entity.board.CropCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Embeddable
@NoArgsConstructor
public class Item {
    private String itemName;
    @Column(name = "itemPrice")
    private int price;
    private int gram;
    private boolean isAvailable = true;

    public Item(String itemName, int price, int gram){
        this.itemName = itemName;
        this.price = price;
        this.gram = gram;
    }
}
