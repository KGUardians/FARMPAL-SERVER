package com.example.farmeasyserver.entity.board.market;

import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.PostType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MarketPost extends Post {
    @Lob
    private String content;
    @Embedded
    private Item item;

    public MarketPost(){
        super(PostType.MARKET);
    }
    public MarketPost(String content, Item item) {
        super(PostType.MARKET);
        this.content = content;
        this.item = item;
    }
}
