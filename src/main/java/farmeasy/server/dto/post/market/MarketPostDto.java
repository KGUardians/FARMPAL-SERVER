package farmeasy.server.dto.post.market;

import farmeasy.server.dto.ImageDto;
import farmeasy.server.entity.board.market.Item;
import farmeasy.server.entity.board.market.MarketPost;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class MarketPostDto {
    private Long postId;
    private String title;
    private MarketAuthorDto author;
    private int postLike;
    private List<ImageDto> imageList;
    private int viewCount;
    private Item item;

    public static MarketPostDto toDto(MarketPost post){
        return MarketPostDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .author(MarketAuthorDto.toDto(post.getAuthor()))
                .postLike(post.getPostLike())
                .viewCount(post.getViewCount())
                .imageList(post.getImageList().stream()
                        .map(i -> ImageDto.toDto(i, i.getId()))
                        .collect(Collectors.toList()))
                .item(post.getItem())
                .build();
    }
}
