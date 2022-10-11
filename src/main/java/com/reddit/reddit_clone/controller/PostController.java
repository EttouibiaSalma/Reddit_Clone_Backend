package com.reddit.reddit_clone.controller;

import com.reddit.reddit_clone.dto.PostRequest;
import com.reddit.reddit_clone.dto.PostResponse;
import com.reddit.reddit_clone.model.Post;
import com.reddit.reddit_clone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity addPost(@RequestBody PostRequest post){
        return new ResponseEntity(postService.createPost(post), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts(){
        return new ResponseEntity<>(postService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return new ResponseEntity<>(postService.get(id), HttpStatus.OK);
    }

    @GetMapping("/subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostBySubreddit(@PathVariable Long id){
        return new ResponseEntity<>(postService.getBySubreddit(id), HttpStatus.OK);
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<List<PostResponse>> getPostByUsername(@PathVariable String name){
        return new ResponseEntity<>(postService.getByUsername(name), HttpStatus.OK);
    }
}
