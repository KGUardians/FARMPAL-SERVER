package com.example.farmeasyserver.dto.post.market;

import com.example.farmeasyserver.dto.post.UpdatePostRequest;

import com.example.farmeasyserver.entity.board.market.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateMktPostReq extends UpdatePostRequest {
    @Schema(description = "상품 이름")
    private String itemName;
    @Schema(description = "상품 가격")
    private int price;
    @Schema(description = "상품 무게")
    private int gram;

    public static Item reqToItem(UpdateMktPostReq req){
        return new Item(
                req.getItemName(),
                req.getPrice(),
                req.getGram()
        );
    }
}
