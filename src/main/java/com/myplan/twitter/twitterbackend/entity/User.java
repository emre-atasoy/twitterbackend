package com.myplan.twitter.twitterbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique = true,nullable = false,length = 50)
    private String username;

    @Column(name = "email", unique = true,nullable = false,length = 100)
    private String email;


    @Column(name="password",nullable = false)
    private String password;



    @Column(name = "created_at",nullable = false)
    private Instant createdAt = Instant.now();


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Tweet> tweets = new ArrayList<>();

}
