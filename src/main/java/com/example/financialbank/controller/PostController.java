package com.example.financialbank.controller;

import com.example.financialbank.dto.CommentView;
import com.example.financialbank.dto.CreateCommentDTO;
import com.example.financialbank.dto.CreatePostDTO;
import com.example.financialbank.dto.PostView;
import com.example.financialbank.model.User;
import com.example.financialbank.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/social/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public PostView create(@AuthenticationPrincipal User user, @Valid @RequestBody CreatePostDTO dto) {
        return postService.createPost(user, dto);
    }

    @GetMapping("/feed")
    public List<PostView> feed(@AuthenticationPrincipal User user) {
        return postService.getFeed(user);
    }

    @GetMapping("/by/{handle}")
    public List<PostView> byHandle(@AuthenticationPrincipal User user, @PathVariable String handle) {
        return postService.getPostsByHandle(handle, user);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal User user, @PathVariable Long postId) {
        postService.deletePost(user, postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> toggleLike(@AuthenticationPrincipal User user, @PathVariable Long postId) {
        postService.toggleLike(user, postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{postId}/comments")
    public CommentView addComment(@AuthenticationPrincipal User user, @PathVariable Long postId,
                                   @Valid @RequestBody CreateCommentDTO dto) {
        return postService.addComment(user, postId, dto);
    }

    @GetMapping("/{postId}/comments")
    public List<CommentView> getComments(@PathVariable Long postId) {
        return postService.getComments(postId);
    }
}
