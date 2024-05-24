package com.example.farmeasyserver.controller.post;

import com.example.farmeasyserver.dto.post.community.comment.CommentRequest;
import com.example.farmeasyserver.dto.post.community.CommunityPostRequest;
import com.example.farmeasyserver.dto.post.community.UpdateComPostReq;
import com.example.farmeasyserver.dto.response.Response;
import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.community.CommunityFilter;
import com.example.farmeasyserver.service.post.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private final PostService postService;

    @GetMapping("/{type}")
    @Operation(summary = "커뮤니티 게시글 리스트 불러오기")
    public Response getCommunityPostList(@PathVariable(value = "type") CommunityType type,
                                          @RequestParam(value = "crop", required = false) CropCategory crop,
                                          @RequestParam(value = "search", required = false) String search,
                                          Pageable pageable){
        CommunityFilter filter = new CommunityFilter(type,crop,search);
        return Response.success(postService.getCommunityPostList(filter,pageable));
    }

    @PostMapping("/post")
    @Operation(summary = "커뮤니티 게시글 등록")
    public Response createPost(@RequestParam(value = "type") CommunityType communityType,
            @Valid @ModelAttribute CommunityPostRequest req, @AuthenticationPrincipal User author) {
        return Response.success(postService.createCommunityPost(req, communityType, author));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "커뮤니티 게시글 삭제")
    public Response deletePost(@PathVariable Long postId, @AuthenticationPrincipal User user){
        return Response.success(postService.deleteCommunityPost(postId,user));
    }

    @Operation(summary = "커뮤니티 게시글 조회")
    @GetMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public Response readPost(@ApiParam(value = "게시글 id", required = true) @PathVariable Long postId) {
        return Response.success(postService.readCommunityPost(postId));
    }

    @PutMapping("/update/{postId}")
    @Operation(summary = "커뮤니티 게시글 수정")
    private Response updatePost(@PathVariable Long postId, @Valid @ModelAttribute UpdateComPostReq req,
                            @AuthenticationPrincipal User user){
        return Response.success(postService.updateCommunityPost(postId,req,user));
    }

    @PostMapping("/comment/{postId}")
    @Operation(summary = "커뮤니티 게시글 댓글 작성")
    public Response comment(@PathVariable Long postId, @RequestBody CommentRequest req, @AuthenticationPrincipal User user) throws ChangeSetPersister.NotFoundException {
        return Response.success(postService.requestComment(postId, req, user));
    }

}
