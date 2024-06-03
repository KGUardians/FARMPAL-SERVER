package farmeasy.server.dto.post.market;

import farmeasy.server.dto.ImageDto;
import farmeasy.server.entity.board.market.Item;
import farmeasy.server.entity.board.market.MarketPost;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class MarketPostDto {
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
                post.getImageList().stream().map(i->ImageDto.toDto(i,i.getId())).collect(Collectors.toList()),
                post.getItem()
        );
    }
}
