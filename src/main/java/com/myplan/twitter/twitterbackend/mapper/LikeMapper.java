package com.myplan.twitter.twitterbackend.mapper;

import com.myplan.twitter.twitterbackend.dto.response.LikeResponse;
import com.myplan.twitter.twitterbackend.entity.Like;

public class LikeMapper {

    public static LikeResponse toResponse(Like like) {
        if (like == null) return null;

        return new LikeResponse(
                like.getId(),
                like.getUser().getUsername(),
                like.getTweet().getId(),
                like.getCreatedAt()
        );
    }
}
