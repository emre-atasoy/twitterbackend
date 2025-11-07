package com.myplan.twitter.twitterbackend.dto.request;

public record CreateCommentRequest(Long userId, Long tweetId, String content) {}
