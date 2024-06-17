package farmeasy.server.entity.board.market;

import farmeasy.server.entity.board.Post;
import farmeasy.server.entity.board.PostType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class MarketPost extends Post {
    @Lob
    private String content;
    @Embedded
    private Item item;

    public MarketPost(){
        super(PostType.MARKET);
    }
    public MarketPost(String content, Item item) {
        super(PostType.MARKET);
        this.content = content;
        this.item = item;
    }
}
