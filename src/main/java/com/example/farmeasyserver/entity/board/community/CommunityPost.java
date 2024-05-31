package com.example.farmeasyserver.entity.board.community;

import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.board.PostType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class CommunityPost extends Post {

    @Enumerated(EnumType.STRING)
    private CommunityType communityType;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    public CommunityPost() {
        super(PostType.COMMUNITY);
    }

    public CommunityPost(CommunityType communityType){
        super(PostType.COMMUNITY);
        this.communityType = communityType;
    }
}
