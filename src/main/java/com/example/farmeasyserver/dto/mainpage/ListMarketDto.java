package com.example.farmeasyserver.dto.mainpage;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.dto.post.ListPostDto;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ListMarketDto extends ListPostDto {
    private String sigungu;
    private String crop;
    private int price;
    private int gram;
    private ImageDto image;

    public ListMarketDto(Long postId,String sigungu, String crop, int price, int gram, int postLike){
        super(postId,postLike);
        this.sigungu = sigungu;
        this.crop = crop;
        this.price = price;
    }

    public static ListMarketDto toDto(MarketPost post){
        return new ListMarketDto(
                post.getId(),
                post.getAuthor().getAddress().getSigungu(),
                post.getItem().getItemName(),
                post.getItem().getPrice(),
                post.getItem().getGram(),
                post.getPostLike()
        );
    }
}
