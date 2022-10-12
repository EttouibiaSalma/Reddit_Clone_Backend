package com.reddit.reddit_clone.repository;

import com.reddit.reddit_clone.model.Post;
import com.reddit.reddit_clone.model.User;
import com.reddit.reddit_clone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
