package farmeasy.server.post.controller;

import farmeasy.server.post.domain.CropCategory;
import farmeasy.server.post.dto.CreatePostResponse;
import farmeasy.server.post.dto.market.CreateMktPostRequest;
import farmeasy.server.post.dto.market.MarketListDto;
import farmeasy.server.post.dto.market.MarketPostDto;
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
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/market")
@RequiredArgsConstructor
public class MarketController {

    private final MarketPostService marketPostService;

    @GetMapping
    @Operation(summary = "마켓 리스트 목록 조회")
    public ResponseEntity<Slice<MarketListDto>> getMarketPostList(
            @RequestParam(value = "crop",required = false) CropCategory crop,
            Pageable pageable
    ){
        MarketFilter filter = new MarketFilter(crop);
        Slice<MarketListDto> response = marketPostService.getMarketPosts(filter, pageable);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "마켓 게시글 작성")
    public ResponseEntity<CreatePostResponse> createPost(
            @Valid @RequestPart CreateMktPostRequest req,
            @AuthenticationPrincipal User author
    ) {
        CreatePostResponse response = marketPostService.createMarketPost(req, author);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "마켓 게시글 삭제")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal User author
    ){
        marketPostService.deleteMarketPost(postId, author);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "마켓 게시글 수정")
    public ResponseEntity<MarketPostDto> updatePost(
            @PathVariable Long postId,
            @Valid @ModelAttribute UpdateMktPostReq req,
            @AuthenticationPrincipal User author){
        marketPostService.updateMarketPost(postId,req,author);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "커뮤니티 게시글 조회", notes = "게시글을 조회한다.")
    @GetMapping("/{postId}")
    @Operation(summary = "마켓 게시글 조회")
    public ResponseEntity<MarketPostDto> readPost(@ApiParam(value = "게시글 id", required = true) @PathVariable Long postId) {
        MarketPostDto response = marketPostService.readMarketPost(postId);
        return ResponseEntity.ok(response);
    }
}
