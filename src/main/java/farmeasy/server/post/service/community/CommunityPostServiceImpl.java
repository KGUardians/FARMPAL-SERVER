package farmeasy.server.post.service.community;

import farmeasy.server.post.domain.community.Comment;
import farmeasy.server.post.domain.community.CommunityPost;
import farmeasy.server.post.dto.CreatePostResponse;
import farmeasy.server.post.dto.community.CommunityListDto;
import farmeasy.server.post.dto.community.CommunityPostDto;
import farmeasy.server.post.dto.community.CreateCommPostRequest;
import farmeasy.server.post.dto.community.UpdateCommPostReq;
import farmeasy.server.post.dto.community.comment.CommentRequest;
import farmeasy.server.post.service.ImageMappingService;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.repository.community.CommentJpaRepo;
import farmeasy.server.post.repository.community.CommunityFilter;
import farmeasy.server.post.repository.community.CommunityJpaRepo;
import farmeasy.server.post.repository.community.CommunityRepo;
import farmeasy.server.post.service.PostService;
import farmeasy.server.util.PostUtil;
import farmeasy.server.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityPostServiceImpl implements CommunityPostService {

    private final CommunityJpaRepo communityJpaRepo;
    private final CommunityRepo communityRepo;
    private final CommentJpaRepo commentJpaRepo;
    private final PostService postService;
    private final ImageMappingService imageMappingService;
    private final PostUtil postUtil;

    @Override
    public List<CommunityListDto> getRecentCommunityPostDtos() {
        List<CommunityPost> recentCommunityPosts = communityJpaRepo.findTop5OrderByIdDesc();
        List<CommunityListDto> list = convertCommunityPostsToDtos(recentCommunityPosts);
        postUtil.commentMapping(list);
        return list;
    }

    @Override
    @Transactional
    public ResponseEntity<CreatePostResponse> createCommunityPost(CreateCommPostRequest req, User author) {
        CommunityPost communityPost = postService.createPost(CreateCommPostRequest.toEntity(req.getCommunityType()), req, author);
        communityJpaRepo.save(communityPost);

        CreatePostResponse createPostResponse = CreatePostResponse.builder()
                .postId(communityPost.getId())
                .postType(communityPost.getPostType())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createPostResponse);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteCommunityPost(Long postId, User author) {
        CommunityPost post = getCommunityPost(postId);
        postService.deletePost(post,author);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Transactional
    public ResponseEntity<CommunityPostDto> readCommunityPost(Long postId){
        return ResponseEntity.ok(CommunityPostDto.toDto(getCommunityPost(postId)));
    }


    @Override
    public ResponseEntity<CommunityPostDto> updateCommunityPost(Long postId, UpdateCommPostReq req, User author) {
        CommunityPost post = getCommunityPost(postId);
        postService.updatePost(author, post, req);
        post.setCommunityType(req.getType());
        communityJpaRepo.save(post);
        return ResponseEntity.ok(CommunityPostDto.toDto(post));
    }

    @Override
    public ResponseEntity<Slice<CommunityListDto>> getCommunityPosts(CommunityFilter filter, Pageable pageable) {
        Slice<CommunityListDto> listResponse = communityRepo.findPostList(filter,pageable);
        imageMappingService.imageMapping(listResponse.stream().toList());
        return ResponseEntity.ok(listResponse);
    }

    /*

    커뮤니티 게시글 댓글 작성

    */
    @Override
    @Transactional
    public ResponseEntity<CommentRequest> requestComment(Long postId, CommentRequest req, User author) {
        CommunityPost post = getCommunityPost(postId);
        Comment comment = new Comment(req.getComment(),post,author);
        commentJpaRepo.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(req);
    }


    private CommunityPost getCommunityPost(Long postId){
        CommunityPost communityPost = communityJpaRepo.findByIdWithUser(postId)
                .orElseThrow(()-> new ResourceNotFoundException("CommunityPost", "communityPost", null));
        communityPost.increaseViewCount();

        return communityPost;
    }

    private List<CommunityListDto> convertCommunityPostsToDtos(List<CommunityPost> recentCommunityPosts){
        return recentCommunityPosts.stream()
                .map(CommunityListDto::toDto)
                .toList();
    }

}
