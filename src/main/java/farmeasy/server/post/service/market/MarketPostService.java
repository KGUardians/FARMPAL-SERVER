package farmeasy.server.post.service.market;

import farmeasy.server.post.dto.CreatePostResponse;
import farmeasy.server.post.dto.market.CreateMktPostRequest;
import farmeasy.server.post.dto.market.MarketListDto;
import farmeasy.server.post.dto.market.MarketPostDto;
import farmeasy.server.post.dto.market.UpdateMktPostReq;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.repository.market.MarketFilter;
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
