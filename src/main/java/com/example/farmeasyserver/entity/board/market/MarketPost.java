package com.example.farmeasyserver.entity.board.market;

import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.market.item.Item;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@DiscriminatorValue("MARKET")
@Data
public class MarketPost extends Post {
    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Item item;
}
