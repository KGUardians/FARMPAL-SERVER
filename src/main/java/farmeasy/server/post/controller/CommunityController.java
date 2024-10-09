package farmeasy.server.post.controller;

import farmeasy.server.post.domain.CropCategory;
import farmeasy.server.post.domain.community.CommunityType;
import farmeasy.server.post.dto.CreatePostResponse;
import farmeasy.server.post.dto.community.CommunityListDto;
import farmeasy.server.post.dto.community.CommunityPostDto;
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
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Slice<CommunityListDto>> getCommunityPostList(
            @RequestParam(value = "type",defaultValue = "QUESTION") CommunityType type,
            @RequestParam(value = "crop", required = false) CropCategory crop,
            @RequestParam(value = "search", required = false) String search,
            Pageable pageable
    ){
        CommunityFilter filter = new CommunityFilter(type,crop,search);

        Slice<CommunityListDto> response = communityPostService.getCommunityPosts(filter, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "커뮤니티 게시글 등록")
    public ResponseEntity<CreatePostResponse> createPost(
            @Valid @RequestPart CreateCommPostRequest req,
            @AuthenticationPrincipal User author
    ) {
        CreatePostResponse response = communityPostService.createCommunityPost(req, author);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "커뮤니티 게시글 삭제")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal User author
    ){
        communityPostService.deleteCommunityPost(postId, author);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "커뮤니티 게시글 조회")
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommunityPostDto> readPost(@ApiParam(value = "게시글 id", required = true) @PathVariable Long postId) {
        CommunityPostDto response = communityPostService.readCommunityPost(postId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "커뮤니티 게시글 수정")
    private ResponseEntity<CommunityPostDto> updatePost(
            @PathVariable Long postId,
            @Valid @ModelAttribute UpdateCommPostReq req,
            @AuthenticationPrincipal User author
    ){
        CommunityPostDto response = communityPostService.updateCommunityPost(postId, req, author);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{postId}/comments")
    @Operation(summary = "커뮤니티 게시글 댓글 작성")
    public ResponseEntity<CommentRequest> comment(
            @PathVariable Long postId,
            @RequestBody CommentRequest req,
            @AuthenticationPrincipal User author) throws ChangeSetPersister.NotFoundException {
        communityPostService.requestComment(postId, req, author);
        return ResponseEntity.ok(req);
    }

}
