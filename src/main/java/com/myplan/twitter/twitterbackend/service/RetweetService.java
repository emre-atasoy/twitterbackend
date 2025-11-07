package com.myplan.twitter.twitterbackend.service;

import com.myplan.twitter.twitterbackend.entity.Retweet;

import java.util.List;

public interface RetweetService {

    Retweet retweet(Long userId, Long tweetId);


    void unretweet(Long userId, Long tweetId);


    List<Retweet> getRetweetsByUser(Long userId);


    List<Retweet> getRetweetsByTweet(Long tweetId);
}
