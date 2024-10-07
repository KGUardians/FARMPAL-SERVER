package com.example.farmeasyserver.post.domain.community;

import farmeasy.server.post.domain.PostType;
import farmeasy.server.post.domain.community.Comment;
import farmeasy.server.post.domain.community.CommunityPost;
import farmeasy.server.post.domain.community.CommunityType;
import farmeasy.server.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommunityPostTest {

    private CommunityPost communityPost;
    private User author;
    private Comment comment1;
    private Comment comment2;

    @BeforeEach
    void setUp() {
        // CommunityPost 객체 생성
        communityPost = new CommunityPost(CommunityType.QUESTION);

        // User 객체 생성 (가상 사용자)
        author = new User();
        author.setUsername("testUser");

        // Comment 객체 생성 및 CommunityPost에 추가
        comment1 = new Comment("This is the first comment.", communityPost, author);
        comment2 = new Comment("This is the second comment.", communityPost, author);
    }

    @Test
    void testCommunityPostCreation() {
        // CommunityPost가 정상적으로 생성되었는지 확인
        assertEquals(PostType.COMMUNITY, communityPost.getPostType());
        assertEquals(CommunityType.QUESTION, communityPost.getCommunityType());
        assertNotNull(communityPost.getCommentList());
        assertEquals(2, communityPost.getCommentList().size());
    }

    @Test
    void testAddCommentToCommunityPost() {
        // 새로운 댓글을 CommunityPost에 추가
        Comment comment3 = new Comment("This is a new comment.", communityPost, author);

        assertEquals(3, communityPost.getCommentList().size());
        assertTrue(communityPost.getCommentList().contains(comment3));
        assertEquals(communityPost, comment3.getPost());
    }

    @Test
    void testRemoveCommentFromCommunityPost() {
        // 댓글을 CommunityPost에서 제거
        communityPost.getCommentList().remove(comment1);

        assertEquals(1, communityPost.getCommentList().size());
        assertFalse(communityPost.getCommentList().contains(comment1));
    }

    @Test
    void testCommentAuthorRelationship() {
        // Comment의 작성자(author)가 제대로 설정되었는지 확인
        assertEquals(author, comment1.getAuthor());
        assertTrue(author.getComments().contains(comment1));
    }

}
