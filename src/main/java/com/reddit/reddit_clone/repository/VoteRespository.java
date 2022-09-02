package com.reddit.reddit_clone.repository;

import com.reddit.reddit_clone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRespository extends JpaRepository<Vote, Long> {
}
