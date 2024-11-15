package farmeasy.server.controller.post;

import farmeasy.server.dto.post.market.CreateMktPostRequest;
import farmeasy.server.dto.post.market.UpdateMktPostReq;
import farmeasy.server.dto.response.Response;
import farmeasy.server.entity.board.CropCategory;
import farmeasy.server.entity.user.User;
import farmeasy.server.repository.post.market.MarketFilter;
import farmeasy.server.service.post.market.MarketPostService;
import farmeasy.server.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/market")
@RequiredArgsConstructor
public class MarketController {

    private final MarketPostService marketPostService;
    private final UserService userService;

    @GetMapping
    @Operation(summary = "마켓 리스트 목록 조회")
    public Response getMarketPostList(@RequestParam(value = "crop",required = false) CropCategory crop,
                                       Pageable pageable){
        MarketFilter filter = new MarketFilter(crop);
        return Response.success(marketPostService.getMarketPosts(filter, pageable));
    }

    @PostMapping
    @Operation(summary = "마켓 게시글 작성")
    public Response createPost(@Valid @ModelAttribute CreateMktPostRequest req) {
        User author = userService.getByUsername();
        return Response.success(marketPostService.createMarketPost(req, author));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "마켓 게시글 삭제")
    public Response deletePost(@PathVariable Long postId){
        User author = userService.getByUsername();
        return Response.success(marketPostService.deleteMarketPost(postId, author));
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "마켓 게시글 수정")
    public Response updatePost(@PathVariable Long postId, @Valid @ModelAttribute UpdateMktPostReq req){
        User author = userService.getByUsername();
        return Response.success(marketPostService.updateMarketPost(postId,req,author));
    }

    @ApiOperation(value = "커뮤니티 게시글 조회", notes = "게시글을 조회한다.")
    @GetMapping("/{postId}")
    @Operation(summary = "마켓 게시글 조회")
    public Response readPost(@ApiParam(value = "게시글 id", required = true) @PathVariable Long postId) {
        return Response.success(marketPostService.readMarketPost(postId));
    }
}
