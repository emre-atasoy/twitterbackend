package com.myplan.twitter.twitterbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "retweets",uniqueConstraints = @UniqueConstraint(columnNames = {"tweet_id","user_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Retweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at",nullable = false)
        private Instant createdAt = Instant.now();



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id",nullable = false)
    private Tweet tweet;
}
