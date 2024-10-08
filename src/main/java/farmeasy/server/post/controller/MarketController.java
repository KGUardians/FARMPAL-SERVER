package farmeasy.server.post.controller;

import farmeasy.server.dto.response.Response;
import farmeasy.server.post.domain.CropCategory;
import farmeasy.server.post.dto.market.CreateMktPostRequest;
import farmeasy.server.post.dto.market.UpdateMktPostReq;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.repository.market.MarketFilter;
import farmeasy.server.post.service.market.MarketPostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/market")
@RequiredArgsConstructor
public class MarketController {

    private final MarketPostService marketPostService;

    @GetMapping
    @Operation(summary = "마켓 리스트 목록 조회")
    public Response getMarketPostList(
            @RequestParam(value = "crop",required = false) CropCategory crop,
            Pageable pageable
    ){
        MarketFilter filter = new MarketFilter(crop);
        return Response.success(marketPostService.getMarketPosts(filter, pageable));
    }

    @PostMapping
    @Operation(summary = "마켓 게시글 작성")
    public Response createPost(
            @Valid @RequestPart CreateMktPostRequest req,
            @AuthenticationPrincipal User author
    ) {
        return Response.success(marketPostService.createMarketPost(req, author));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "마켓 게시글 삭제")
    public Response deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal User author
    ){
        return Response.success(marketPostService.deleteMarketPost(postId, author));
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "마켓 게시글 수정")
    public Response updatePost(
            @PathVariable Long postId,
            @Valid @ModelAttribute UpdateMktPostReq req,
            @AuthenticationPrincipal User author){
        return Response.success(marketPostService.updateMarketPost(postId,req,author));
    }

    @ApiOperation(value = "커뮤니티 게시글 조회", notes = "게시글을 조회한다.")
    @GetMapping("/{postId}")
    @Operation(summary = "마켓 게시글 조회")
    public Response readPost(@ApiParam(value = "게시글 id", required = true) @PathVariable Long postId) {
        return Response.success(marketPostService.readMarketPost(postId));
    }
}
