package com.example.farmeasyserver.controller.post;

import com.example.farmeasyserver.dto.post.market.MarketPostRequest;
import com.example.farmeasyserver.dto.post.market.UpdateMarPostReq;
import com.example.farmeasyserver.dto.response.Response;
import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.market.MarketFilter;
import com.example.farmeasyserver.service.post.PostService;
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

    private final PostService postService;

    @GetMapping
    @Operation(summary = "마켓 리스트 목록 조회")
    public Response getMarketPostList(@RequestParam(value = "crop",required = false) CropCategory crop,
                                       Pageable pageable){
        MarketFilter filter = new MarketFilter(crop);
        return Response.success(postService.getMarketPostList(filter,pageable));
    }

    @PostMapping
    @Operation(summary = "마켓 게시글 작성")
    public Response createPost(@Valid @ModelAttribute MarketPostRequest req, @AuthenticationPrincipal User author) {
        return Response.success(postService.createMarketPost(req, author));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "마켓 게시글 삭제")
    public Response deletePost(@PathVariable Long postId, @AuthenticationPrincipal User user){
        return Response.success(postService.deleteMarketPost(postId,user));
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "마켓 게시글 수정")
    public Response updatePost(@PathVariable Long postId, @Valid @ModelAttribute UpdateMarPostReq req,
                           @AuthenticationPrincipal User user){
        return Response.success(postService.updateMarketPost(postId,req,user));
    }

    @ApiOperation(value = "커뮤니티 게시글 조회", notes = "게시글을 조회한다.")
    @GetMapping("/{postId}")
    @Operation(summary = "마켓 게시글 조회")
    public Response readPost(@ApiParam(value = "게시글 id", required = true) @PathVariable Long postId) {
        return Response.success(postService.readMarketPost(postId));
    }
}
