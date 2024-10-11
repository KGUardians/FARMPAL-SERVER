package farmeasy.server.post.service.market;

import farmeasy.server.post.domain.market.MarketPost;
import farmeasy.server.post.dto.CreatePostResponse;
import farmeasy.server.post.dto.market.CreateMktPostRequest;
import farmeasy.server.post.dto.market.MarketListDto;
import farmeasy.server.post.dto.market.MarketPostDto;
import farmeasy.server.post.dto.market.UpdateMktPostReq;
import farmeasy.server.post.service.ImageMappingService;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.repository.market.MarketFilter;
import farmeasy.server.post.repository.market.MarketJpaRepo;
import farmeasy.server.post.repository.market.MarketRepo;
import farmeasy.server.post.service.PostService;
import farmeasy.server.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketPostServiceImpl implements MarketPostService {

    private final MarketJpaRepo marketJpaRepo;
    private final MarketRepo marketRepo;
    private final PostService postService;
    private final ImageMappingService imageMappingService;

    @Override
    public List<MarketListDto> getRecentMarketPostDtos() {
        List<MarketListDto> recentMarketPosts = marketRepo.findTop4OrderByIdDesc();
        imageMappingService.imageMapping(recentMarketPosts);
        return recentMarketPosts;
    }

    @Override
    @Transactional
    public CreatePostResponse createMarketPost(CreateMktPostRequest req, User author) {
        MarketPost marketPost = postService.createPost(CreateMktPostRequest.toEntity(req), req, author);
        marketJpaRepo.save(marketPost);

        return CreatePostResponse.builder()
                .postId(marketPost.getId())
                .postType(marketPost.getPostType())
                .build();
    }

    @Override
    @Transactional
    public MarketPostDto readMarketPost(Long postId){
        return MarketPostDto.toDto(getMarketPost(postId));
    }

    @Override
    @Transactional
    public void updateMarketPost(Long postId, UpdateMktPostReq req, User author) {
        MarketPost post = getMarketPost(postId);
        postService.updatePost(author, post, req);
        post.setItem(UpdateMktPostReq.reqToItem(req));
        marketJpaRepo.save(post);
    }

    @Override
    @Transactional
    public void deleteMarketPost(Long postId, User author) {
        MarketPost post = getMarketPost(postId);
        postService.deletePost(post,author);
    }

    @Override
    public Slice<MarketListDto> getMarketPosts(MarketFilter filter, Pageable pageable) {
        Slice<MarketListDto> listResponse = marketRepo.findPostList(filter, pageable);
        imageMappingService.imageMapping(listResponse.stream().toList());
        return listResponse;
    }

    private MarketPost getMarketPost(Long postId){
        MarketPost marketPost = marketJpaRepo.findByIdWithUser(postId)
                .orElseThrow(() -> new ResourceNotFoundException("ExperiencePost", "experiencePost", null));
        marketPost.increaseViewCount();

        return marketPost;
    }
}