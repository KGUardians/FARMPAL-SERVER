package com.example.farmeasyserver.entity.board.item;

import com.example.farmeasyserver.entity.board.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private Post post;
    private String itemName;
    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;
    @Column(name = "itemPrice")
    private int price;
    private int gram;
    private boolean isAvailable = true;

    public Item(String itemName, ItemCategory itemCategory, int price, int gram){
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.price = price;
        this.gram = gram;
    }
}
