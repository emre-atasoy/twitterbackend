package com.myplan.twitter.twitterbackend.service;

import com.myplan.twitter.twitterbackend.entity.User;

import java.util.List;

public interface UserService {
    User createUser(String username, String email, String password);
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    void deleteUser(Long id,Long currentUserId);
}
