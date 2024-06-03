package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.post.CreatePostRequest;
import com.example.farmeasyserver.dto.post.UpdatePostRequest;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.user.User;

public interface PostService {

    <T extends Post> T createPost(T p, CreatePostRequest req, User author);

    void deletePost(Post post, User author);

    void updatePost(User author, Post post, UpdatePostRequest req);
}
