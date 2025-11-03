package com.myplan.twitter.twitterbackend.service;

import com.myplan.twitter.twitterbackend.entity.Comment;
import com.myplan.twitter.twitterbackend.entity.Tweet;
import com.myplan.twitter.twitterbackend.entity.User;
import com.myplan.twitter.twitterbackend.repository.CommentRepository;
import com.myplan.twitter.twitterbackend.repository.TweetRepository;
import com.myplan.twitter.twitterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{


    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    @Autowired
    public CommentServiceImpl(TweetRepository tweetRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }


    @Override
    public Comment createComment(Long userId, Long tweetId, String content) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı"));
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(()-> new RuntimeException("Tweet bulunamadı"));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setTweet(tweet);
        comment.setContent(content);
        comment.setCreatedAt(Instant.now());
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long commentId, Long currentUserId, String newContent) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("Yorum bulunamadı"));
        if(!comment.getUser().getId().equals(currentUserId)){
            throw new RuntimeException("Bu yorumu düzenleme yetkiniz yok!");
        }
        comment.setContent(newContent);
        comment.setUpdatedAt(Instant.now());
        return commentRepository.save(comment);
    }


    @Transactional
    @Override
    public void deleteComment(Long commentId, Long currentUserId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("Yorum bulunamadı"));
        if(!comment.getUser().getId().equals(currentUserId)){
            throw new RuntimeException("Bu yorumu silmeye yetkiniz yok!");
        }
        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> getCommentByTweet(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(()-> new RuntimeException("Tweet bulunamadı!"));

        return commentRepository.findByTweet(tweet);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("Yorum bulunamadı!"));
    }
}
