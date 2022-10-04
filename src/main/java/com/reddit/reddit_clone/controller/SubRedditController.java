package com.reddit.reddit_clone.controller;

import com.reddit.reddit_clone.dto.SubRedditDTO;
import com.reddit.reddit_clone.service.SubRedditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubRedditController {

    private final SubRedditService subRedditService;

    @PostMapping
    public ResponseEntity<SubRedditDTO> createSubReddit(@RequestBody SubRedditDTO subReddit){
        return new ResponseEntity<>(subRedditService.save(subReddit), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SubRedditDTO>> getAllSubReddits(){
        return new ResponseEntity<>(subRedditService.getAll(), HttpStatus.OK);
    }
}
