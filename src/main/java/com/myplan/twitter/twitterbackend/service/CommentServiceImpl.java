package com.myplan.twitter.twitterbackend.service;

import com.myplan.twitter.twitterbackend.entity.Comment;
import com.myplan.twitter.twitterbackend.entity.Tweet;
import com.myplan.twitter.twitterbackend.entity.User;
import com.myplan.twitter.twitterbackend.exception.ApiException;
import com.myplan.twitter.twitterbackend.repository.CommentRepository;
import com.myplan.twitter.twitterbackend.repository.TweetRepository;
import com.myplan.twitter.twitterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException("Kullanıcı bulunamadı!", HttpStatus.NOT_FOUND));
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiException("Tweet bulunamadı!", HttpStatus.NOT_FOUND));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setTweet(tweet);
        comment.setContent(content);
        comment.setCreatedAt(Instant.now());
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long commentId, Long currentUserId, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException("Yorum bulunamadı!", HttpStatus.NOT_FOUND));

        if (!comment.getUser().getId().equals(currentUserId)) {
            throw new ApiException("Bu yorumu düzenleme yetkiniz yok!", HttpStatus.FORBIDDEN);
        }

        comment.setContent(newContent);
        comment.setUpdatedAt(Instant.now());
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId, Long currentUserId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException("Yorum bulunamadı!", HttpStatus.NOT_FOUND));

        if (!comment.getUser().getId().equals(currentUserId)) {
            throw new ApiException("Bu yorumu silmeye yetkiniz yok!", HttpStatus.FORBIDDEN);
        }

        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> getCommentByTweet(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ApiException("Tweet bulunamadı!", HttpStatus.NOT_FOUND));

        return commentRepository.findByTweet(tweet);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException("Yorum bulunamadı!", HttpStatus.NOT_FOUND));
    }
}
