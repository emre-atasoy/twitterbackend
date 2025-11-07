package com.myplan.twitter.twitterbackend.controller;

import com.myplan.twitter.twitterbackend.dto.request.*;
import com.myplan.twitter.twitterbackend.dto.response.TweetResponse;
import com.myplan.twitter.twitterbackend.entity.Tweet;
import com.myplan.twitter.twitterbackend.entity.User;
import com.myplan.twitter.twitterbackend.exception.ApiException;
import com.myplan.twitter.twitterbackend.mapper.TweetMapper;
import com.myplan.twitter.twitterbackend.repository.UserRepository;
import com.myplan.twitter.twitterbackend.service.TweetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {
    private final UserRepository userRepository;
    private final TweetService tweetService;

    @Autowired
    public TweetController(UserRepository userRepository, TweetService tweetService) {
        this.userRepository = userRepository;
        this.tweetService = tweetService;
    }

    //Tweet oluştur
    @PostMapping
    public TweetResponse createTweet(@Valid @RequestBody CreateTweetRequest request) {
        Tweet tweet = tweetService.createTweet(request.userId(), request.content());
        return TweetMapper.toResponse(tweet);
    }

    // Kullanıcının tweetlerini getir
    @GetMapping("/user/username/{username}")
    public List<TweetResponse> getTweetsByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException("Kullanıcı bulunamadı", HttpStatus.NOT_FOUND));

        return tweetService.getTweetsByUser(user.getId())
                .stream()
                .map(TweetMapper::toResponse)
                .toList();
    }

    // Tek tweet getir
    @GetMapping("/{id}")
    public TweetResponse getTweetById(@PathVariable Long id) {
        Tweet tweet = tweetService.getTweetById(id);
        return TweetMapper.toResponse(tweet);
    }

    // Tweet güncelle
    @PutMapping
    public TweetResponse updateTweet(@Valid @RequestBody UpdateTweetRequest request) {
        Tweet tweet = tweetService.updateTweet(
                request.tweetId(),
                request.currentUserId(),
                request.content()
        );
        return TweetMapper.toResponse(tweet);
    }

    @DeleteMapping
    public TweetResponse deleteTweet(@Valid @RequestBody DeleteTweetRequest request) {
        Tweet tweet = tweetService.getTweetById(request.tweetId());
        tweetService.deleteTweet(request.tweetId(), request.currentUserId());
        return TweetMapper.toResponse(tweet);
    }
}
