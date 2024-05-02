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
@NoArgsConstructor
public class MarketPost extends Post {
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
    @Embedded
    private Item item;
    @OneToMany(mappedBy = "m_post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();

    public MarketPost(String title, String content, Item item) {
        this.title = title;
        this.content = content;
        this.item = item;
    }

     public void addImages(List<Image> added) {
        added.stream().forEach(i -> {
            imageList.add(i);
            i.setPost(this);
        });
    }

    public void setAuthor(User author){
        this.author = author;
        author.getMarketPosts().add(this);
    }
}
