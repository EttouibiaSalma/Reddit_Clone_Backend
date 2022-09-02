package com.reddit.reddit_clone.repository;

import com.reddit.reddit_clone.model.subReddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface subRedditRepository extends JpaRepository<subReddit, Long> {
}
