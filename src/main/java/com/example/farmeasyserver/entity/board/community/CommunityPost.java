package com.example.farmeasyserver.entity.board.community;

import com.example.farmeasyserver.entity.board.Post;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("COMMUNITY")
@Data
public class CommunityPost extends Post {


    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "community_type")
    private CommunityType communityType;

}
