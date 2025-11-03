package com.myplan.twitter.twitterbackend.service;

import com.myplan.twitter.twitterbackend.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Long userId,Long tweetId,String content);
    Comment updateComment(Long commentId,Long currentUserId,String newContent);
    void deleteComment(Long commentId,Long currentUserId);
    List<Comment> getCommentByTweet(Long tweetId);
    Comment getCommentById(Long commentId);
}
