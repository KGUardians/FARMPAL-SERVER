package farmeasy.server.service.post.community;

import farmeasy.server.dto.post.CreatePostResponse;
import farmeasy.server.dto.post.community.CommunityListDto;
import farmeasy.server.dto.post.community.CommunityPostDto;
import farmeasy.server.dto.post.community.CreateCommPostRequest;
import farmeasy.server.dto.post.community.UpdateCommPostReq;
import farmeasy.server.dto.post.community.comment.CommentRequest;
import farmeasy.server.entity.user.User;
import farmeasy.server.repository.post.community.CommunityFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommunityPostService {

    List<CommunityListDto> getRecentCommunityPostDtos();

    CreatePostResponse createCommunityPost(CreateCommPostRequest req, User author);

    Long deleteCommunityPost(Long postId, User user);

    CommunityPostDto readCommunityPost(Long postId);

    CommunityPostDto updateCommunityPost(Long postId, UpdateCommPostReq req, User user);

    Slice<CommunityListDto> getCommunityPosts(CommunityFilter filter, Pageable pageable);

    CommentRequest requestComment(Long postId, CommentRequest req, User user);
}
