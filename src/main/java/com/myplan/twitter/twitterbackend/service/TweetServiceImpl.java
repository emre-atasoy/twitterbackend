package com.myplan.twitter.twitterbackend.service;

import com.myplan.twitter.twitterbackend.entity.Tweet;
import com.myplan.twitter.twitterbackend.entity.User;
import com.myplan.twitter.twitterbackend.repository.TweetRepository;
import com.myplan.twitter.twitterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;



@Service
public class TweetServiceImpl implements TweetService{

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Tweet createTweet(Long userId, String content) {
        User user =userRepository.
                findById(userId).orElseThrow(() -> new RuntimeException(("Kullanıcı bulunamadı.Anonim tweet atılamaz!")));

        Tweet tweet = new Tweet();
        tweet.setContent(content);
        tweet.setUser(user);
        tweet.setCreatedAt(Instant.now());
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> getTweetsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı"));
        return tweetRepository.findByUser(user);
    }

    @Override
    public Tweet getTweetById(Long id) {
        return tweetRepository.findById(id).orElseThrow(()-> new RuntimeException("Tweet bulunamadı"));
    }

    @Override
    public Tweet updateTweet(Long id,Long currentUserId, String content) {
        Tweet tweet = tweetRepository.findById(id).orElseThrow(()-> new RuntimeException("Tweet bulunamadı"));
        if(!tweet.getUser().getId().equals(currentUserId)){
            throw new RuntimeException("Bu tweeti güncelleme yetkiniz yok");
        }
        tweet.setContent(content);
        tweet.setUpdatedAt(Instant.now());
        return tweetRepository.save(tweet);
    }

    @Override
    public void deleteTweet(Long id,Long currentUserId) {
        Tweet tweet = tweetRepository.findById(id).orElseThrow(()-> new RuntimeException("Tweet bulunamadı"));

        if(!tweet.getUser().getId().equals(currentUserId)){
            throw new RuntimeException("Bu tweeti silmeye yetkiniz yok");
        }
        tweetRepository.delete(tweet);
    }
}
