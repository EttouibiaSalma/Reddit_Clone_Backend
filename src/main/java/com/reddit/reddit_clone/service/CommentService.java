package com.reddit.reddit_clone.service;

import com.reddit.reddit_clone.dto.CommentDTO;
import com.reddit.reddit_clone.exception.ApplicationException;
import com.reddit.reddit_clone.mapper.CommentMapper;
import com.reddit.reddit_clone.model.Comment;
import com.reddit.reddit_clone.model.NotificationEmail;
import com.reddit.reddit_clone.model.Post;
import com.reddit.reddit_clone.model.User;
import com.reddit.reddit_clone.repository.CommentRepository;
import com.reddit.reddit_clone.repository.PostRepository;
import com.reddit.reddit_clone.repository.UserRepository;
import javafx.geometry.Pos;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper mapper;
    private final MailService mailService;
    private final MailContentBuilder contentBuilder;

    public Comment saveComment(CommentDTO commentDTO){
        Post post = postRepository.findById(commentDTO.getPostId()).orElseThrow(()-> new ApplicationException("Post with id: "+commentDTO.getPostId() + " not found"));
        Comment comment = mapper.map(commentDTO, post, authService.getCurrentUser());
        commentRepository.save(comment);
        String message = contentBuilder.builder(post.getUser().getUsername() + "posted a comment on your post");
        sendCommentNotification(post.getUser(), message);
        return comment;
    }

    private void sendCommentNotification(User user, String message) {
        mailService.sendEmail(new NotificationEmail(user.getUsername() + "commented on your post",
                user.getEmail(), message));
    }

    public List<CommentDTO> getByPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ApplicationException("post not found"));
        return commentRepository.findByPost(post)
                .stream()
                .map(mapper::mapToDTO)
                .collect(toList());
    }

    public List<CommentDTO> getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
        return commentRepository.findByUser(user)
                .stream()
                .map(mapper::mapToDTO)
                .collect(toList());
    }
}
