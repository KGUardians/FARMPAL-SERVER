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
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MarketPostService {
    List<MarketListDto> getRecentMarketPostDtos();

    ResponseEntity<CreatePostResponse> createMarketPost(CreateMktPostRequest req, User user);

    ResponseEntity<MarketPostDto> readMarketPost(Long postId);

    ResponseEntity<MarketPostDto> updateMarketPost(Long postId, UpdateMktPostReq req, User user);

    ResponseEntity<Void> deleteMarketPost(Long postId, User author);

    ResponseEntity<Slice<MarketListDto>> getMarketPosts(MarketFilter filter, Pageable pageable);
}
