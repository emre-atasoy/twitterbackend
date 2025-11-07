package com.myplan.twitter.twitterbackend.dto.response;

import java.time.Instant;

public record RetweetResponse(
        Long id,
        String username,
        Long tweetId,
        Instant createdAt
) {}
