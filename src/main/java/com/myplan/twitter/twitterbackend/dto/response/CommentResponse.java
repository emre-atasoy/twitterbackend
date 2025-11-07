package com.myplan.twitter.twitterbackend.dto.response;

import java.time.Instant;

public record CommentResponse(
        Long id,
        String content,
        String username,
        Long tweetId,
        Instant createdAt,
        Instant updatedAt
) {}
