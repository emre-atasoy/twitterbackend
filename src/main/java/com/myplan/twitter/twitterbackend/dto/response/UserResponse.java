package com.myplan.twitter.twitterbackend.dto.response;

import java.time.Instant;

public record UserResponse(Long id, String username, String email, Instant createdAt) {}
