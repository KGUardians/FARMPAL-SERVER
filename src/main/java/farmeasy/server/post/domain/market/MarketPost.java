package farmeasy.server.post.domain.market;

import farmeasy.server.post.domain.Post;
import farmeasy.server.post.domain.PostType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
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
