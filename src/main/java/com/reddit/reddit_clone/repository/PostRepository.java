package com.reddit.reddit_clone.repository;

import com.reddit.reddit_clone.model.Post;
import com.reddit.reddit_clone.model.SubReddit;
import com.reddit.reddit_clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(SubReddit subreddit);

    List<Post> findByUser(User user);
}
