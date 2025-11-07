package com.myplan.twitter.twitterbackend.mapper;

import com.myplan.twitter.twitterbackend.dto.response.CommentResponse;
import com.myplan.twitter.twitterbackend.entity.Comment;

public class CommentMapper {

    public static CommentResponse toResponse(Comment comment) {
        if (comment == null) return null;

        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getUsername(),
                comment.getTweet().getId(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
