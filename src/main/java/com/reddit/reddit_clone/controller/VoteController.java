package com.reddit.reddit_clone.controller;

import com.reddit.reddit_clone.dto.VoteDTO;
import com.reddit.reddit_clone.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity vote(@RequestBody VoteDTO voteDTO){
        voteService.vote(voteDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public void get(){

    }
}
