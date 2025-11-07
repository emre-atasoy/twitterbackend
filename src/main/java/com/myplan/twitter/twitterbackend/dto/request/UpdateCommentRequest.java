package com.myplan.twitter.twitterbackend.dto.request;

public record UpdateCommentRequest(Long commentId, Long userId, String newContent) {}
