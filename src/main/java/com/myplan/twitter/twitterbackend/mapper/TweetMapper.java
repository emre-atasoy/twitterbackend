package com.myplan.twitter.twitterbackend.mapper;

import com.myplan.twitter.twitterbackend.dto.response.TweetResponse;
import com.myplan.twitter.twitterbackend.entity.Tweet;

public class TweetMapper {

    public static TweetResponse toResponse(Tweet tweet){
        if(tweet == null) return null;


        return new TweetResponse(
                tweet.getId(),
                tweet.getContent(),
                tweet.getUser().getUsername(),
                tweet.getCreatedAt()
        );
    }
}
