package com.example.farmeasyserver.dto.post.market;

import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import com.example.farmeasyserver.dto.mainpage.ListPostDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ListMarketDto extends ListPostDto {
    private String sigungu;
    private int price;
    private int gram;

    public ListMarketDto(Long postId,String sigungu, CropCategory cropCategory, int price, int gram, int postLike){
        super(postId,postLike,cropCategory);
        this.sigungu = sigungu;
        this.price = price;
        this.gram = gram;
    }

   /* public static ListMarketDto toDto(MarketPost post){
        return new ListMarketDto(
                post.getId(),
                post.getAuthor().getAddress().getSigungu(),
                post.getCropCategory(),
                post.getItem().getPrice(),
                post.getItem().getGram(),
                post.getPostLike()
        );
    }*/
}
