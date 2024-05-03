package com.example.farmeasyserver.entity.board.market;

import com.example.farmeasyserver.entity.board.Image;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@DiscriminatorValue("MARKET")
@NoArgsConstructor
public class MarketPost extends Post {
    @Lob
    private String content;
    @Embedded
    private Item item;

    public MarketPost(String content, Item item) {
        this.content = content;
        this.item = item;
    }
}
