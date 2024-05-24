package com.example.farmeasyserver.dto.post.market;

import com.example.farmeasyserver.dto.post.CreatePostRequest;
import com.example.farmeasyserver.entity.board.market.Item;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class MarketPostRequest extends CreatePostRequest {

    @Schema(description = "상품 이름", example = "item")
    @NotBlank(message = "물건 이름을 입력해주세요.")
    private String itemName;

    @Schema(description = "상품 가격", example = "50000")
    @NotNull(message = "가격을 입력해주세요.")
    @PositiveOrZero(message = "0원 이상을 입력해주세요")
    private int price;

    @Schema(description = "상품 무게", example = "50000")
    @NotNull(message = "작물 무게를 입력해주세요.")
    @PositiveOrZero(message = "0 이상을 입력해주세요")
    private int gram;

    private static Item reqToItem(MarketPostRequest req){
        return new Item(
                req.getItemName(),
                req.getPrice(),
                req.getGram()
        );
    }

    public static MarketPost toEntity(MarketPostRequest req){
        return new MarketPost(req.getContent(),MarketPostRequest.reqToItem(req));
    }

}
