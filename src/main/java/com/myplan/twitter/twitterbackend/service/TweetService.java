package com.myplan.twitter.twitterbackend.service;

import com.myplan.twitter.twitterbackend.entity.Tweet;

import java.util.List;
import java.util.Optional;

public interface TweetService {
    Tweet createTweet(Long userId,String content);
    List<Tweet> getTweetsByUser(Long UserId);
    Tweet getTweetById(Long id);
    Tweet updateTweet(Long id,Long currentUserId,String content);
    void deleteTweet(Long id,Long currentUserId);
}
