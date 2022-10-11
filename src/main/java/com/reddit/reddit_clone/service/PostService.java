package com.reddit.reddit_clone.service;

import com.reddit.reddit_clone.dto.PostRequest;
import com.reddit.reddit_clone.dto.PostResponse;
import com.reddit.reddit_clone.exception.ApplicationException;
import com.reddit.reddit_clone.mapper.PostMapper;
import com.reddit.reddit_clone.model.Post;
import com.reddit.reddit_clone.model.SubReddit;
import com.reddit.reddit_clone.model.User;
import com.reddit.reddit_clone.repository.PostRepository;
import com.reddit.reddit_clone.repository.SubRedditRepository;
import com.reddit.reddit_clone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubRedditRepository subRedditRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final PostMapper mapper;

    public Post createPost(PostRequest request) {
        SubReddit subReddit = subRedditRepository.findByName(request.getPostName())
                .orElseThrow(()-> new ApplicationException("SubReddit not found"));
        User user = authService.getCurrentUser();
        return mapper.map(request, subReddit, user);
        //postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAll() {
        return postRepository.findAll()
                .stream()
                .map(mapper::mapToDTO)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Post with id: " + id.toString()+ " not found"));
        return mapper.mapToDTO(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getBySubreddit(Long id) {
        SubReddit subreddit = subRedditRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Subreddit with id: " + id.toString() + " not found"));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(mapper::mapToDTO).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getByUsername(String name) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException(name));
        return postRepository.findByUser(user)
                .stream()
                .map(mapper::mapToDTO)
                .collect(toList());
    }
}
