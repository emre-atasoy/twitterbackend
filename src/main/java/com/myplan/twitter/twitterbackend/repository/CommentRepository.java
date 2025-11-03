package com.myplan.twitter.twitterbackend.repository;

import com.myplan.twitter.twitterbackend.entity.Comment;
import com.myplan.twitter.twitterbackend.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByTweet(Tweet tweet);
}
