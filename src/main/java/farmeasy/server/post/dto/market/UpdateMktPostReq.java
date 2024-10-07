package farmeasy.server.post.dto.market;

import farmeasy.server.post.domain.market.Item;
import farmeasy.server.post.dto.UpdatePostRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMktPostReq extends UpdatePostRequest {
    @Schema(description = "상품 이름")
    private String itemName;
    @Schema(description = "상품 가격")
    private int price;
    @Schema(description = "상품 무게")
    private int gram;

    public static Item reqToItem(UpdateMktPostReq req){
        return Item.builder()
                .itemName(req.getItemName())
                .price(req.getPrice())
                .gram(req.getGram())
                .build();
    }
}
