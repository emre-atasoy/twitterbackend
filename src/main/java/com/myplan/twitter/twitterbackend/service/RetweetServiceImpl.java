package com.myplan.twitter.twitterbackend.service;

import com.myplan.twitter.twitterbackend.entity.Retweet;
import com.myplan.twitter.twitterbackend.entity.Tweet;
import com.myplan.twitter.twitterbackend.entity.User;
import com.myplan.twitter.twitterbackend.repository.RetweetRepository;
import com.myplan.twitter.twitterbackend.repository.TweetRepository;
import com.myplan.twitter.twitterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
@Service
public class RetweetServiceImpl implements RetweetService{

    private final RetweetRepository retweetRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Autowired
    public RetweetServiceImpl(RetweetRepository retweetRepository, TweetRepository tweetRepository, UserRepository userRepository) {
        this.retweetRepository = retweetRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    @Override
    public Retweet retweet(Long userId, Long tweetId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı!"));

        if (retweetRepository.existsByUserAndTweet(user, tweet)) {
            throw new RuntimeException("Bu tweet zaten retweet edilmiş!");
        }

        Retweet retweet = new Retweet();
        retweet.setUser(user);
        retweet.setTweet(tweet);
        retweet.setCreatedAt(Instant.now());

        return retweetRepository.save(retweet);
    }


    @Transactional
    @Override
    public void undoRetweet(Long userId, Long tweetId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı!"));

        Retweet retweet = retweetRepository.findByTweetAndUser(tweet, user)
                .orElseThrow(() -> new RuntimeException("Retweet bulunamadı!"));

        retweetRepository.delete(retweet);
    }

    @Override
    public List<Retweet> getRetweetsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));
        return retweetRepository.findByUser(user);
    }

    public List<Retweet> getRetweetsByTweet(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı!"));
        return retweetRepository.findByTweet(tweet);
    }
}
