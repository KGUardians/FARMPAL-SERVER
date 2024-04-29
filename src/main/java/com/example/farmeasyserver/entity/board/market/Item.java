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
    @Enumerated(EnumType.STRING)
    private CropCategory cropCategory;
    @Column(name = "itemPrice")
    private int price;
    private int gram;
    private boolean isAvailable = true;

    public Item(String itemName, CropCategory cropCategory, int price, int gram){
        this.itemName = itemName;
        this.cropCategory = cropCategory;
        this.price = price;
        this.gram = gram;
    }
}
