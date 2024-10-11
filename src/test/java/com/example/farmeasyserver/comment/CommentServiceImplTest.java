package com.example.farmeasyserver.comment;

import farmeasy.server.comment.domain.Comment;
import farmeasy.server.comment.dto.CommentRequest;
import farmeasy.server.comment.repository.CommentJpaRepo;
import farmeasy.server.comment.service.CommentServiceImpl;
import farmeasy.server.post.domain.Post;
import farmeasy.server.post.domain.market.MarketPost;
import farmeasy.server.post.repository.PostJpaRepo;
import farmeasy.server.user.domain.User;
import farmeasy.server.util.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceImplTest {

    @Mock
    private CommentJpaRepo commentJpaRepo;

    @Mock
    private PostJpaRepo postJpaRepo;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Post post;
    private User author;
    private User anotherUser;
    private Comment comment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Mockito 초기화

        // 기본 테스트 데이터 생성
        post = new MarketPost();
        post.setId(1L);

        author = new User();
        author.setId(1L);  // 댓글 작성자

        anotherUser = new User();
        anotherUser.setId(2L);  // 다른 유저

        comment = new Comment("Test Comment", post, author);
        comment.setId(1L);
    }

    @Test
    void 댓글작성_성공() {
        // Given
        CommentRequest commentRequest = new CommentRequest("New Comment");

        when(postJpaRepo.findById(post.getId())).thenReturn(Optional.of(post));

        // When
        commentService.requestComment(post.getId(), commentRequest, author);

        // Then
        verify(commentJpaRepo, times(1)).save(any(Comment.class));
    }

    @Test
    void 댓글작성_게시글없음() {
        // Given
        CommentRequest commentRequest = new CommentRequest("New Comment");

        when(postJpaRepo.findById(post.getId())).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ResourceNotFoundException.class, () ->
                commentService.requestComment(post.getId(), commentRequest, author));

        verify(commentJpaRepo, times(0)).save(any(Comment.class));
    }

    @Test
    void 댓글삭제_성공() {
        // Given
        when(commentJpaRepo.findById(comment.getId())).thenReturn(Optional.of(comment));

        // When
        commentService.deleteComment(comment.getId(), author);

        // Then
        verify(commentJpaRepo, times(1)).delete(comment);
    }

    @Test
    void 댓글삭제_권한없음() {

        when(commentJpaRepo.findById(comment.getId())).thenReturn(Optional.of(comment));

        // When / Then
        assertThrows(AccessDeniedException.class, () ->
                commentService.deleteComment(comment.getId(), anotherUser));

        verify(commentJpaRepo, times(0)).delete(comment);
    }

    @Test
    void 댓글수정_성공() {
        // Given
        String updatedContent = "Updated comment";

        when(commentJpaRepo.findById(comment.getId())).thenReturn(Optional.of(comment));

        // When
        commentService.updateComment(comment.getId(), updatedContent, author);

        // Then
        verify(commentJpaRepo, times(1)).save(comment);
        assertEquals(updatedContent, comment.getComment());
    }

    @Test
    void 댓글수정_권한없음() {
        String updatedContent = "Updated comment";

        when(commentJpaRepo.findById(comment.getId())).thenReturn(Optional.of(comment));

        // When / Then
        assertThrows(AccessDeniedException.class, () ->
                commentService.updateComment(comment.getId(), updatedContent, anotherUser));

        verify(commentJpaRepo, times(0)).save(comment);
    }
}