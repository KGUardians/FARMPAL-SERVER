package com.example.farmeasyserver.dto.post;

import com.example.farmeasyserver.entity.user.Address;
import lombok.Data;

import java.util.List;

@Data
public class RuralExpPostDto extends PostDto{
    private Address address;
    public RuralExpPostDto(Long id, String title, String author,int like, Address address){
        this.setPostId(id);
        this.setTitle(title);
        this.setAuthor(author);
        this.setLike(like);
        this.address = address;
    }
}
