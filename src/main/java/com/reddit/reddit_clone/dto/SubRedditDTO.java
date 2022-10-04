package com.reddit.reddit_clone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubRedditDTO {
    private Long id;
    private String name;
    private String description;
    private int numberOfPosts;
}
