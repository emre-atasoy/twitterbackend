package com.myplan.twitter.twitterbackend.dto.response;

import java.time.Instant;

public record TweetResponse(Long id, String content, String username, Instant createdAt) {}
