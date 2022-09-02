package com.reddit.reddit_clone.repository;

import com.reddit.reddit_clone.model.comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<comment, Long> {
}