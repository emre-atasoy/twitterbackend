package com.myplan.twitter.twitterbackend.service;

import com.myplan.twitter.twitterbackend.entity.User;

public interface AuthService {

    User register(String username,String email,String password);

    String login(String username,String password);
}
