package com.myplan.twitter.twitterbackend.repository;

import com.myplan.twitter.twitterbackend.entity.Like;
import com.myplan.twitter.twitterbackend.entity.Tweet;
import com.myplan.twitter.twitterbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findByTweetAndUser(Tweet tweet, User user);
}
