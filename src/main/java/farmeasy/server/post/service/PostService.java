package farmeasy.server.post.service;

import farmeasy.server.post.domain.Post;
import farmeasy.server.post.dto.CreatePostRequest;
import farmeasy.server.post.dto.UpdatePostRequest;
import farmeasy.server.user.domain.User;

public interface PostService {

    <T extends Post> T createPost(T p, CreatePostRequest req, User author);

    void deletePost(Post post, User author);

    void updatePost(User author, Post post, UpdatePostRequest req);
}
