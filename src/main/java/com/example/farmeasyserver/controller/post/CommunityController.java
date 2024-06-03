package com.example.farmeasyserver.controller.post;

import com.example.farmeasyserver.dto.post.community.comment.CommentRequest;
import com.example.farmeasyserver.dto.post.community.CreateCommPostRequest;
import com.example.farmeasyserver.dto.post.community.UpdateCommPostReq;
import com.example.farmeasyserver.dto.response.Response;
import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.community.CommunityFilter;
import com.example.farmeasyserver.service.post.community.CommunityPostService;
import com.example.farmeasyserver.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "Community Post Controller", tags = "CommunityPost")
@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityPostService communityPostService;
    private final UserService userService;


    @GetMapping
    @Operation(summary = "커뮤니티 게시글 리스트 불러오기")
    public Response getCommunityPostList(@RequestParam(value = "type",defaultValue = "QUESTION") CommunityType type,
                                          @RequestParam(value = "crop", required = false) CropCategory crop,
                                          @RequestParam(value = "search", required = false) String search,
                                          Pageable pageable){
        CommunityFilter filter = new CommunityFilter(type,crop,search);
        return Response.success(communityPostService.getCommunityPosts(filter,pageable));
    }

    @PostMapping
    @Operation(summary = "커뮤니티 게시글 등록")
    public Response createPost(
            @Valid @ModelAttribute CreateCommPostRequest req) {
        User author = userService.getByUsername();
        return Response.success(communityPostService.createCommunityPost(req, author));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "커뮤니티 게시글 삭제")
    public Response deletePost(@PathVariable Long postId){
        User author = userService.getByUsername();
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
    private Response updatePost(@PathVariable Long postId, @Valid @ModelAttribute UpdateCommPostReq req){
        User author = userService.getByUsername();
        return Response.success(communityPostService.updateCommunityPost(postId, req, author));
    }

    @PostMapping("/{postId}/comments")
    @Operation(summary = "커뮤니티 게시글 댓글 작성")
    public Response comment(@PathVariable Long postId, @RequestBody CommentRequest req) throws ChangeSetPersister.NotFoundException {
        User author = userService.getByUsername();
        return Response.success(communityPostService.requestComment(postId, req, author));
    }

}
