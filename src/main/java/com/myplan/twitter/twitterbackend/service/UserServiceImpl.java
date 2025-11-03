package com.myplan.twitter.twitterbackend.service;

import com.myplan.twitter.twitterbackend.entity.User;
import com.myplan.twitter.twitterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User createUser(String username, String email, String password) {
        if(userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("Bu kullanıcı adı zaten alınmış!");
        }
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Bu e-posta adresi zaten kayıtlı!");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));     // passwordu hasledim.
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(Long id, Long currentUserId) {

            User user = userRepository.findById(id).
                    orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı"));
            if(!user.getId().equals(currentUserId)){
                throw new RuntimeException("Sadece kendi hesabınızı silebilirsiniz");

            }
            userRepository.delete(user);
    }
}
