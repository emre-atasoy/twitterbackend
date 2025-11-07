package com.myplan.twitter.twitterbackend.controller;

import com.myplan.twitter.twitterbackend.dto.request.RetweetRequest;
import com.myplan.twitter.twitterbackend.dto.response.RetweetResponse;
import com.myplan.twitter.twitterbackend.entity.Retweet;
import com.myplan.twitter.twitterbackend.service.RetweetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retweets")
public class RetweetController {

    private final RetweetService retweetService;

    @Autowired
    public RetweetController(RetweetService retweetService) {
        this.retweetService = retweetService;
    }

    // Retweet yap
    @PostMapping
    public RetweetResponse retweet(@Valid @RequestBody RetweetRequest request) {
        Retweet retweet = retweetService.retweet(request.userId(), request.tweetId());
        return new RetweetResponse(
                retweet.getId(),
                retweet.getUser().getUsername(),
                retweet.getTweet().getId(),
                retweet.getCreatedAt()
        );
    }

    // Retweet kaldır
    @DeleteMapping("/{id}")
    public String undoRetweet(@PathVariable Long id, @RequestParam Long userId) {
        retweetService.unretweet(userId, id);
        return "Retweet kaldırıldı 🔁";
    }

    // Tweetin retweetleri
    @GetMapping("/tweet/{tweetId}")
    public List<RetweetResponse> getRetweetsByTweet(@PathVariable Long tweetId) {
        return retweetService.getRetweetsByTweet(tweetId)
                .stream()
                .map(retweet -> new RetweetResponse(
                        retweet.getId(),
                        retweet.getUser().getUsername(),
                        retweet.getTweet().getId(),
                        retweet.getCreatedAt()
                ))
                .toList();
    }

    // Kullanıcının retweetleri
    @GetMapping("/user/{userId}")
    public List<RetweetResponse> getRetweetsByUser(@PathVariable Long userId) {
        return retweetService.getRetweetsByUser(userId)
                .stream()
                .map(retweet -> new RetweetResponse(
                        retweet.getId(),
                        retweet.getUser().getUsername(),
                        retweet.getTweet().getId(),
                        retweet.getCreatedAt()
                ))
                .toList();
    }
}
