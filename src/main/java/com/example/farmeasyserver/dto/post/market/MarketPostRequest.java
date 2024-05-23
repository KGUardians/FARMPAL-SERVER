package com.example.farmeasyserver.dto.post.market;

import com.example.farmeasyserver.dto.post.CreatePostRequest;
import com.example.farmeasyserver.entity.board.market.Item;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class MarketPostRequest extends CreatePostRequest {

    @ApiModelProperty(value = "물건 이름", notes = "물건 이름을 입력해주세요", required = true, example = "item")
    @NotBlank(message = "물건 이름을 입력해주세요.")
    private String itemName;

    @ApiModelProperty(value = "가격", notes = "가격을 입력해주세요", required = true, example = "50000")
    @NotNull(message = "가격을 입력해주세요.")
    @PositiveOrZero(message = "0원 이상을 입력해주세요")
    private int price;

    @ApiModelProperty(value = "무게", notes = "무게를 입력해주세요", required = true, example = "50000")
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
