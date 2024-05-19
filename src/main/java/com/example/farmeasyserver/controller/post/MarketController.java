package com.example.farmeasyserver.controller.post;

import com.example.farmeasyserver.dto.post.market.MarketPostRequest;
import com.example.farmeasyserver.dto.response.Response;
import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.market.MarketFilter;
import com.example.farmeasyserver.service.post.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/market")
@RequiredArgsConstructor
public class MarketController {

    private final PostService postService;

    @GetMapping
    public Response readMarketPostList(@RequestParam(value = "crop",required = false) CropCategory crop,
                                       Pageable pageable){
        MarketFilter filter = new MarketFilter(crop);
        return Response.success(postService.getMarketPostList(filter,pageable));
    }

    @PostMapping("/post")
    public Response create(@Valid @ModelAttribute MarketPostRequest req, @AuthenticationPrincipal User author) {
        return Response.success(postService.createMarketPost(req, author));
    }

    @DeleteMapping("/post/{postId}")
    public Response delete(@PathVariable Long postId, @AuthenticationPrincipal User user){
        return Response.success(postService.deleteMarketPost(postId,user));
    }

    @ApiOperation(value = "커뮤니티 게시글 조회", notes = "게시글을 조회한다.")
    @GetMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public Response read(@ApiParam(value = "게시글 id", required = true) @PathVariable Long postId) throws ChangeSetPersister.NotFoundException {
        return Response.success(postService.readMarketPost(postId));
    }
}
