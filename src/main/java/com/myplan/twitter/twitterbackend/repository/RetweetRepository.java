package com.myplan.twitter.twitterbackend.repository;

import com.myplan.twitter.twitterbackend.entity.Retweet;
import com.myplan.twitter.twitterbackend.entity.Tweet;
import com.myplan.twitter.twitterbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RetweetRepository extends JpaRepository<Retweet,Long> {
    Optional<Retweet> findByTweetAndUser(Tweet tweet, User user);
    Boolean existsByUserAndTweet(User user, Tweet tweet);
    List<Retweet> findByUser(User user);
    List<Retweet> findByTweet(Tweet tweet);
}
