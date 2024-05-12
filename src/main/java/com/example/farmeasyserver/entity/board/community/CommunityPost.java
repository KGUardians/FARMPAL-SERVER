package com.example.farmeasyserver.entity.board.community;

import com.example.farmeasyserver.entity.board.Post;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CommunityPost extends Post {

    @Enumerated(EnumType.STRING)
    @Column(name = "community_type")
    private CommunityType communityType;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    public CommunityPost(CommunityType communityType) {
        this.communityType = communityType;
    }

    public void addComment(Comment comment){
        commentList.add(comment);
        comment.setPost(this);
    }

}
