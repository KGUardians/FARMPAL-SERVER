package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.dto.PostImageQueryDto;
import com.example.farmeasyserver.entity.user.Address;
import lombok.Data;

@Data
public class ImagePostDto {
    private Long postId;
    private String title;
    private String author;
    private int like;
    private int price;
    private int gram;
    private Address address;
    private PostImageQueryDto image;

    public ImagePostDto(Long postId, String title, String author, int like, int price, int gram, Address address){
        this.postId = postId;
        this.title = title;
        this.author = author;
        this.like = like;
        this.price = price;
        this.gram = gram;
        this.address = address;
    }
}
