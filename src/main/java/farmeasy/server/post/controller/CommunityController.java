package farmeasy.server.post.controller;

import farmeasy.server.dto.response.Response;
import farmeasy.server.post.domain.CropCategory;
import farmeasy.server.post.domain.community.CommunityType;
import farmeasy.server.post.dto.community.CreateCommPostRequest;
import farmeasy.server.post.dto.community.UpdateCommPostReq;
import farmeasy.server.post.dto.community.comment.CommentRequest;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.repository.community.CommunityFilter;
import farmeasy.server.post.service.community.CommunityPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(value = "Community Post Controller", tags = "CommunityPost")
@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityPostService communityPostService;

    @GetMapping
    @Operation(summary = "커뮤니티 게시글 리스트 불러오기")
    public Response getCommunityPostList(
            @RequestParam(value = "type",defaultValue = "QUESTION") CommunityType type,
            @RequestParam(value = "crop", required = false) CropCategory crop,
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable
    ){
        CommunityFilter filter = new CommunityFilter(type,crop,search);
        return Response.success(communityPostService.getCommunityPosts(filter,pageable));
    }

    @PostMapping
    @Operation(summary = "커뮤니티 게시글 등록")
    public Response createPost(
            @Valid @RequestPart CreateCommPostRequest req,
            @AuthenticationPrincipal User author
    ) {
        return Response.success(communityPostService.createCommunityPost(req, author));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "커뮤니티 게시글 삭제")
    public Response deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal User author
    ){
        return Response.success(communityPostService.deleteCommunityPost(postId, author));
    }

    @Operation(summary = "커뮤니티 게시글 조회")
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public Response readPost(@ApiParam(value = "게시글 id", required = true) @PathVariable Long postId) {
        return Response.success(communityPostService.readCommunityPost(postId));
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "커뮤니티 게시글 수정")
    private Response updatePost(
            @PathVariable Long postId,
            @Valid @ModelAttribute UpdateCommPostReq req,
            @AuthenticationPrincipal User author
    ){
        return Response.success(communityPostService.updateCommunityPost(postId, req, author));
    }

    @PostMapping("/{postId}/comments")
    @Operation(summary = "커뮤니티 게시글 댓글 작성")
    public Response comment(
            @PathVariable Long postId,
            @RequestBody CommentRequest req,
            @AuthenticationPrincipal User author) throws ChangeSetPersister.NotFoundException {
        return Response.success(communityPostService.requestComment(postId, req, author));
    }

}
