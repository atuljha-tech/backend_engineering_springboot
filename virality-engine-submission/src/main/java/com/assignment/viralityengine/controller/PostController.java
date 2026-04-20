package com.assignment.viralityengine.controller;

import com.assignment.viralityengine.dto.CreateCommentRequest;
import com.assignment.viralityengine.dto.CreatePostRequest;
import com.assignment.viralityengine.dto.LikePostRequest;
import com.assignment.viralityengine.entity.Comment;
import com.assignment.viralityengine.entity.Post;
import com.assignment.viralityengine.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody CreatePostRequest request) {
        Post post = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long postId,
            @Valid @RequestBody CreateCommentRequest request) {
        Comment comment = postService.addComment(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(
            @PathVariable Long postId,
            @Valid @RequestBody LikePostRequest request) {
        postService.likePost(postId, request);
        return ResponseEntity.ok().build();
    }

}
