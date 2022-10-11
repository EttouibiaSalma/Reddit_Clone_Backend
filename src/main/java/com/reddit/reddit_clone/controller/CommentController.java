package com.reddit.reddit_clone.controller;

import com.reddit.reddit_clone.dto.CommentDTO;
import com.reddit.reddit_clone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity addComment(@RequestBody CommentDTO commentDTO){
        return new ResponseEntity(commentService.saveComment(commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable Long postId){
        return new ResponseEntity<>(commentService.getByPost(postId), HttpStatus.OK);
    }
    @GetMapping("/user/{username}")
    public ResponseEntity<List<CommentDTO>> getCommentsByUsername(@PathVariable String username){
        return new ResponseEntity<>(commentService.getByUsername(username), HttpStatus.OK);
    }
}
