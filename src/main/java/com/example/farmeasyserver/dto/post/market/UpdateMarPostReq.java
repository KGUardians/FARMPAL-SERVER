package com.example.farmeasyserver.dto.post.market;

import com.example.farmeasyserver.dto.post.UpdatePostRequest;

import com.example.farmeasyserver.entity.board.market.Item;
import lombok.Data;

@Data
public class UpdateMarPostReq extends UpdatePostRequest {
    private String itemName;
    private int price;
    private int gram;

    public static Item itemToEntity(UpdateMarPostReq req){
        return new Item(
                req.getItemName(),
                req.getPrice(),
                req.getGram()
        );
    }
}
