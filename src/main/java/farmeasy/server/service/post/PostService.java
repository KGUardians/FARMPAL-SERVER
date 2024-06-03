package farmeasy.server.service.post;

import farmeasy.server.dto.post.CreatePostRequest;
import farmeasy.server.dto.post.UpdatePostRequest;
import farmeasy.server.entity.board.Post;
import farmeasy.server.entity.user.User;

public interface PostService {

    <T extends Post> T createPost(T p, CreatePostRequest req, User author);

    void deletePost(Post post, User author);

    void updatePost(User author, Post post, UpdatePostRequest req);
}
