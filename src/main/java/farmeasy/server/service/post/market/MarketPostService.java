package farmeasy.server.service.post.market;

import farmeasy.server.dto.post.CreatePostResponse;
import farmeasy.server.dto.post.market.CreateMktPostRequest;
import farmeasy.server.dto.post.market.MarketListDto;
import farmeasy.server.dto.post.market.MarketPostDto;
import farmeasy.server.dto.post.market.UpdateMktPostReq;
import farmeasy.server.entity.user.User;
import farmeasy.server.repository.post.market.MarketFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface MarketPostService {
    List<MarketListDto> getRecentMarketPostDtos();

    CreatePostResponse createMarketPost(CreateMktPostRequest req, User user);

    MarketPostDto readMarketPost(Long postId);

    MarketPostDto updateMarketPost(Long postId, UpdateMktPostReq req, User user);

    Long deleteMarketPost(Long postId, User author);

    Slice<MarketListDto> getMarketPosts(MarketFilter filter, Pageable pageable);
}
