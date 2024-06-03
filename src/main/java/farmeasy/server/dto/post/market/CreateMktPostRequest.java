package farmeasy.server.dto.post.market;

import farmeasy.server.dto.post.CreatePostRequest;
import farmeasy.server.entity.board.market.Item;
import farmeasy.server.entity.board.market.MarketPost;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateMktPostRequest extends CreatePostRequest {

    @Schema(description = "상품 이름", example = "item")
    @NotBlank(message = "물건 이름을 입력해주세요.")
    private String itemName;

    @Schema(description = "상품 가격", example = "50000")
    @NotNull(message = "가격을 입력해주세요.")
    @PositiveOrZero(message = "0원 이상을 입력해주세요")
    private int price;

    @Schema(description = "상품 무게", example = "50000")
    @NotNull(message = "작물 무게를 입력해주세요.")
    @PositiveOrZero(message = "0 이상을 입력해주세요")
    private int gram;

    private static Item reqToItem(CreateMktPostRequest req){
        return new Item(
                req.getItemName(),
                req.getPrice(),
                req.getGram()
        );
    }

    public static MarketPost toEntity(CreateMktPostRequest req){
        return new MarketPost(req.getContent(), CreateMktPostRequest.reqToItem(req));
    }

}
