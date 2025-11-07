package com.myplan.twitter.twitterbackend.controller;

import com.myplan.twitter.twitterbackend.dto.request.*;
import com.myplan.twitter.twitterbackend.dto.response.CommentResponse;
import com.myplan.twitter.twitterbackend.entity.Comment;
import com.myplan.twitter.twitterbackend.mapper.CommentMapper;
import com.myplan.twitter.twitterbackend.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Yorum oluştur
    @PostMapping
    public CommentResponse createComment(@Valid @RequestBody CreateCommentRequest request) {
        Comment comment = commentService.createComment(request.userId(), request.tweetId(), request.content());
        return CommentMapper.toResponse(comment);
    }

    //  Yorum güncelle
    @PutMapping
    public CommentResponse updateComment(@Valid @RequestBody UpdateCommentRequest request) {
        Comment comment = commentService.updateComment(request.commentId(), request.userId(), request.newContent());
        return CommentMapper.toResponse(comment);

    }

    // Yorum sil
    @DeleteMapping
    public void deleteComment(@Valid @RequestBody DeleteCommentRequest request) {
        commentService.deleteComment(request.commentId(), request.userId());
    }

    // Tweetin tüm yorumları
    @GetMapping("/tweet/{tweetId}")
    public List<CommentResponse> getCommentsByTweet(@PathVariable Long tweetId) {
        return commentService.getCommentByTweet(tweetId)
                .stream()
                .map(CommentMapper::toResponse)
                .toList();
    }
}
