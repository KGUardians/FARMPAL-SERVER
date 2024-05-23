package com.example.farmeasyserver.entity.board.market;

import com.example.farmeasyserver.dto.post.market.UpdateMarPostReq;
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

    public static Item toEntity(UpdateMarPostReq req){
        return new Item(
                req.getItemName(),
                req.getPrice(),
                req.getGram()
        );
    }
}
