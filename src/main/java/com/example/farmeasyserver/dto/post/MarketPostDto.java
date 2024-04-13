package com.example.farmeasyserver.dto.post;

import com.example.farmeasyserver.entity.board.market.item.Item;
import com.example.farmeasyserver.entity.user.Address;
import lombok.Data;

@Data
public class MarketPostDto extends PostDto{
    private Item item;
    private Address address;
    public MarketPostDto(Long id, String title, int like, Item item, String author, Address address){
        this.setPostId(id);
        this.setTitle(title);
        this.setLike(like);
        this.item = item;
        this.setAuthor(author);
        this.address = address;
    }
}
