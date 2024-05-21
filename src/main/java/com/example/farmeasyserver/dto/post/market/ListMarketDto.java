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
    private String farmName;
    private int price;
    private int gram;

    public ListMarketDto(Long postId, String sigungu, String farmName, CropCategory cropCategory, int price, int gram, int postLike){
        super(postId,postLike,cropCategory);
        this.sigungu = sigungu;
        this.farmName = farmName;
        this.price = price;
        this.gram = gram;
    }

}
