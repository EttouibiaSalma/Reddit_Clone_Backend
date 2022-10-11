package com.reddit.reddit_clone.repository;

import com.reddit.reddit_clone.model.SubReddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubRedditRepository extends JpaRepository<SubReddit, Long> {
    Optional<SubReddit> findByName(String name);
}
