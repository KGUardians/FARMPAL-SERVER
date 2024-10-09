package farmeasy.server.post.service.community;

import farmeasy.server.post.dto.CreatePostResponse;
import farmeasy.server.post.dto.community.CommunityListDto;
import farmeasy.server.post.dto.community.CommunityPostDto;
import farmeasy.server.post.dto.community.CreateCommPostRequest;
import farmeasy.server.post.dto.community.UpdateCommPostReq;
import farmeasy.server.post.dto.community.comment.CommentRequest;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.repository.community.CommunityFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunityPostService {

    List<CommunityListDto> getRecentCommunityPostDtos();

    ResponseEntity<CreatePostResponse> createCommunityPost(CreateCommPostRequest req, User author);

    ResponseEntity<Void> deleteCommunityPost(Long postId, User user);

    ResponseEntity<CommunityPostDto> readCommunityPost(Long postId);

    ResponseEntity<CommunityPostDto> updateCommunityPost(Long postId, UpdateCommPostReq req, User user);

    ResponseEntity<Slice<CommunityListDto>> getCommunityPosts(CommunityFilter filter, Pageable pageable);

    ResponseEntity<CommentRequest> requestComment(Long postId, CommentRequest req, User user);
}
