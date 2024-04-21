package com.example.farmeasyserver.dto.post.market;

import com.example.farmeasyserver.dto.post.PostDto;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.item.Item;
import com.example.farmeasyserver.entity.user.Address;
import lombok.Data;

@Data
public class MarketPostDto extends PostDto {
    private Item item;
    public static MarketPostDto toDto(Post post){
        return new MarketPostDto();
    }
}
