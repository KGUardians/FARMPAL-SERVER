package farmeasy.server.post.service.market;

import farmeasy.server.post.domain.market.MarketPost;
import farmeasy.server.post.dto.CreatePostResponse;
import farmeasy.server.post.dto.market.CreateMktPostRequest;
import farmeasy.server.post.dto.market.MarketListDto;
import farmeasy.server.post.dto.market.MarketPostDto;
import farmeasy.server.post.dto.market.UpdateMktPostReq;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.repository.market.MarketFilter;
import farmeasy.server.post.repository.market.MarketJpaRepo;
import farmeasy.server.post.repository.market.MarketRepo;
import farmeasy.server.file.service.FileService;
import farmeasy.server.post.service.PostService;
import farmeasy.server.util.PostUtil;
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
    private final FileService fileService;
    private final PostUtil postUtil;

    @Override
    public List<MarketListDto> getRecentMarketPostDtos() {
        List<MarketListDto> recentMarketPosts = marketRepo.findTop4OrderByIdDesc();
        fileService.imageMapping(recentMarketPosts); return recentMarketPosts;
    }

    @Override
    @Transactional
    public CreatePostResponse createMarketPost(CreateMktPostRequest req, User author) {
        MarketPost marketPost = postService.createPost(CreateMktPostRequest.toEntity(req), req, author);
        marketJpaRepo.save(marketPost);
        return new CreatePostResponse(marketPost.getId(),marketPost.getPostType());
    }

    @Override
    @Transactional
    public MarketPostDto readMarketPost(Long postId){
        return MarketPostDto.toDto(postUtil.getMarketPost(postId));
    }


    @Override
    @Transactional
    public MarketPostDto updateMarketPost(Long postId, UpdateMktPostReq req, User author) {
        MarketPost post = postUtil.getMarketPost(postId);
        postService.updatePost(author, post, req);
        post.setItem(UpdateMktPostReq.reqToItem(req));
        marketJpaRepo.save(post);
        return MarketPostDto.toDto(post);
    }

    @Override
    @Transactional
    public Long deleteMarketPost(Long postId, User author) {
        MarketPost post = postUtil.getMarketPost(postId);
        postService.deletePost(post,author);
        return postId;
    }

    @Override
    public Slice<MarketListDto> getMarketPosts(MarketFilter filter, Pageable pageable) {
        Slice<MarketListDto> listResponse = marketRepo.findPostList(filter, pageable);
        fileService.imageMapping(listResponse.stream().toList()); return listResponse;
    }

}
