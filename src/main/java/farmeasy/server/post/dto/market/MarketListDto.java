package farmeasy.server.post.dto.market;

import farmeasy.server.main.dto.PostListDto;
import farmeasy.server.post.domain.CropCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MarketListDto extends PostListDto {

    private String sigungu;
    private String farmName;
    private int price;
    private int gram;

    public MarketListDto(Long postId, String sigungu, String farmName, CropCategory cropCategory, int price, int gram, int postLike){
        super(postId,postLike,cropCategory);
        this.sigungu = sigungu;
        this.farmName = farmName;
        this.price = price;
        this.gram = gram;
    }

}
