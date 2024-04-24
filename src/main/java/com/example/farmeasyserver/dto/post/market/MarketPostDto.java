package com.example.farmeasyserver.dto.post.market;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.dto.post.PostDto;
import com.example.farmeasyserver.entity.board.market.Item;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class MarketPostDto extends PostDto {
    private Long postId;
    private String title;
    private MarketAuthorDto author;
    private int postLike;
    private List<ImageDto> imageList;
    private Item item;

    public static MarketPostDto toDto(MarketPost post){
        return new MarketPostDto(
                post.getId(),
                post.getTitle(),
                MarketAuthorDto.toDto(post.getAuthor()),
                post.getPostLike(),
                post.getImageList().stream().map(i->ImageDto.toDto(i)).collect(Collectors.toList()),
                post.getItem()
        );
    }
}
