package com.myplan.twitter.twitterbackend.service;

import com.myplan.twitter.twitterbackend.entity.User;
import com.myplan.twitter.twitterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public User register(String username, String email, String password) {
        if(userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("Kullanıcı adı zaten mevcut!");
        }
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Email zaten kullanılıyor!");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public String login(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        ); // UserDetailsService (bizim CustomUserDetailsService) çağrılır.
        //Bu, veritabanından username’e göre kullanıcıyı bulur.

        //Sonra BCryptPasswordEncoder ile veritabanındaki şifre ile kullanıcının girdiği şifre karşılaştırılır.

        // Eğer eşleşirse, doğrulama başarılı olur → auth.isAuthenticated() true döner.

        //  Eğer yanlışsa, BadCredentialsException fırlatılır (giriş başarısız).



        if(auth.isAuthenticated()){
            return "TOKEN_OLUSTURULDU";
        }else {
            throw new RuntimeException("Giriş başarısız");
        }
    }
}
