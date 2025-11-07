package com.myplan.twitter.twitterbackend.controller;

import com.myplan.twitter.twitterbackend.dto.request.LikeRequest;
import com.myplan.twitter.twitterbackend.dto.response.LikeResponse;
import com.myplan.twitter.twitterbackend.entity.Like;
import com.myplan.twitter.twitterbackend.mapper.LikeMapper;
import com.myplan.twitter.twitterbackend.service.LikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    // Tweet beğen
    @PostMapping
    public LikeResponse likeTweet(@Valid @RequestBody LikeRequest request) {
        Like like = likeService.likeTweet(request.userId(), request.tweetId());
        return LikeMapper.toResponse(like);
    }

    // Beğeniyi kaldır
    @DeleteMapping
    public String dislikeTweet(@Valid @RequestBody LikeRequest request) {
        likeService.unlikeTweet(request.userId(), request.tweetId());
        return "Beğeni kaldırıldı 👎";
    }

    // Tweetin beğenileri
    @GetMapping("/tweet/{tweetId}")
    public List<LikeResponse> getLikesByTweet(@PathVariable Long tweetId) {
        return likeService.getLikesByTweet(tweetId)
                .stream()
                .map(LikeMapper::toResponse)
                .toList();
    }

    // Kullanıcının beğenileri
    @GetMapping("/user/{userId}")
    public List<LikeResponse> getLikesByUser(@PathVariable Long userId) {
        return likeService.getLikesByUser(userId)
                .stream()
                .map(LikeMapper::toResponse)
                .toList();
    }
}
