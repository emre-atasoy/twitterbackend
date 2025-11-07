package com.myplan.twitter.twitterbackend.dto.request;

public record UpdateTweetRequest(Long tweetId, Long currentUserId, String content) {}
