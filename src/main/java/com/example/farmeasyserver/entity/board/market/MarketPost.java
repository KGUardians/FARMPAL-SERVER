package com.example.farmeasyserver.entity.board.market;

import com.example.farmeasyserver.entity.board.Image;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class MarketPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    @Column(name = "post_title",nullable = false)
    private String title;
    @Lob
    private String content;
    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Item item;
    private int postLike;
    @OneToMany(mappedBy = "m_post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList;

    public MarketPost(User author, String title, String content, Item item, List<Image> imageList) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.item = item;
        this.imageList = imageList;
    }
}
