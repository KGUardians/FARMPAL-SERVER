package farmeasy.server.entity.board.market;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Embeddable
@NoArgsConstructor
public class Item {
    private String itemName;
    @Column(name = "itemPrice")
    private int price;
    private int gram;
    private boolean isAvailable = true;

    @Builder
    public Item(String itemName, int price, int gram){
        this.itemName = itemName;
        this.price = price;
        this.gram = gram;
    }




}
