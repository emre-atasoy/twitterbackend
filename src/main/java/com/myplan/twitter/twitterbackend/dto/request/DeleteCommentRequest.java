package com.myplan.twitter.twitterbackend.dto.request;

public record DeleteCommentRequest(
        Long commentId,
        Long userId
) {}
