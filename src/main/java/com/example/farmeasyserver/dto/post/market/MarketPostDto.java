package com.example.farmeasyserver.dto.post.market;

import com.example.farmeasyserver.dto.post.PostDto;
import com.example.farmeasyserver.entity.board.market.Item;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import lombok.Data;

@Data
public class MarketPostDto extends PostDto {
    private Item item;
    public static MarketPostDto toDto(MarketPost post){
        return new MarketPostDto();
    }
}
