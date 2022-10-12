package com.reddit.reddit_clone.service;

import com.reddit.reddit_clone.dto.VoteDTO;
import com.reddit.reddit_clone.exception.ApplicationException;
import com.reddit.reddit_clone.model.Post;
import com.reddit.reddit_clone.model.Vote;
import com.reddit.reddit_clone.repository.PostRepository;
import com.reddit.reddit_clone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.reddit.reddit_clone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final PostRepository postRepository;

    private final VoteRepository voteRepository;
    private final AuthService authService;
    @Transactional
    public void vote(VoteDTO voteDTO) {
        Post post = postRepository.findById(voteDTO.getPostId()).orElseThrow(()->new ApplicationException("Post not found"));
        Optional<Vote> voteByPostAndUSer = voteRepository.findByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUSer.isPresent() && voteByPostAndUSer.get().getVoteType().equals(voteDTO.getVoteType())){
            throw new ApplicationException("You've already voted for this post");
        }
        if (UPVOTE.equals(voteDTO.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        }
        else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDTO, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDTO voteDTO, Post post) {
        return Vote.builder()
                .voteType(voteDTO.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
