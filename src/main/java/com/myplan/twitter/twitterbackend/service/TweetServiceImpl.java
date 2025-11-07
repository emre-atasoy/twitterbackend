package com.myplan.twitter.twitterbackend.service;

import com.myplan.twitter.twitterbackend.entity.Tweet;
import com.myplan.twitter.twitterbackend.entity.User;
import com.myplan.twitter.twitterbackend.exception.ApiException;
import com.myplan.twitter.twitterbackend.repository.TweetRepository;
import com.myplan.twitter.twitterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Tweet createTweet(Long userId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("Kullanıcı bulunamadı. Anonim tweet atılamaz!", HttpStatus.NOT_FOUND));

        Tweet tweet = new Tweet();
        tweet.setContent(content);
        tweet.setUser(user);
        tweet.setCreatedAt(Instant.now());
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> getTweetsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("Kullanıcı bulunamadı!", HttpStatus.NOT_FOUND));
        return tweetRepository.findByUser(user);
    }

    @Override
    public Tweet getTweetById(Long id) {
        return tweetRepository.findById(id)
                .orElseThrow(() -> new ApiException("Tweet bulunamadı!", HttpStatus.NOT_FOUND));
    }

    @Override
    public Tweet updateTweet(Long id, Long currentUserId, String content) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new ApiException("Tweet bulunamadı!", HttpStatus.NOT_FOUND));

        if (!tweet.getUser().getId().equals(currentUserId)) {
            throw new ApiException("Bu tweet'i güncelleme yetkiniz yok!", HttpStatus.FORBIDDEN);
        }

        tweet.setContent(content);
        tweet.setUpdatedAt(Instant.now());
        return tweetRepository.save(tweet);
    }

    @Override
    public void deleteTweet(Long id, Long currentUserId) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new ApiException("Tweet bulunamadı!", HttpStatus.NOT_FOUND));

        if (!tweet.getUser().getId().equals(currentUserId)) {
            throw new ApiException("Bu tweet'i silmeye yetkiniz yok!", HttpStatus.FORBIDDEN);
        }

        tweetRepository.delete(tweet);
    }
}
