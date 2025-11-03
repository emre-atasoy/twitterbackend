package com.myplan.twitter.twitterbackend.service;

import com.myplan.twitter.twitterbackend.entity.Like;

import java.util.List;

public interface LikeService {
    Like likeTweet(Long userId,Long tweetId);

    void unlikeTweet(Long userId,Long tweetId);

    List<Like> getLikesByTweet(Long tweetId);

    List<Like> getLikesByUser(Long userId);
}
