package com.myplan.twitter.twitterbackend.service;

import com.myplan.twitter.twitterbackend.entity.Like;
import com.myplan.twitter.twitterbackend.entity.Tweet;
import com.myplan.twitter.twitterbackend.entity.User;
import com.myplan.twitter.twitterbackend.repository.LikeRepository;
import com.myplan.twitter.twitterbackend.repository.TweetRepository;
import com.myplan.twitter.twitterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, TweetRepository tweetRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Like likeTweet(Long userId, Long tweetId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı"));

        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(()-> new RuntimeException("Tweet bulunamadı"));

        if(likeRepository.existsByUserAndTweet(user,tweet)){
            throw new RuntimeException("Bu tweet zaten beğenilmiş!");
        }
        Like like = new Like();
        like.setUser(user);
        like.setTweet(tweet);
        like.setCreatedAt(Instant.now());
        return likeRepository.save(like);
    }

    @Transactional
    @Override
    public void unlikeTweet(Long userId, Long tweetId) {

        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı!"));
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(()-> new RuntimeException("Tweet bulunamadı!"));

        Like like = likeRepository.findByTweetAndUser(tweet, user).orElseThrow(()-> new RuntimeException("Beğeni bulunamadı!"));

        likeRepository.delete(like);

    }

    @Override
    public List<Like> getLikesByTweet(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(()-> new RuntimeException("Tweet bulunamadı!"));
        return likeRepository.findByTweet(tweet);
    }

    @Override
    public List<Like> getLikesByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı!"));
        return likeRepository.findByUser(user);
    }
}
