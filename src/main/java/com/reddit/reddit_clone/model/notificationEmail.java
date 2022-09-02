package com.reddit.reddit_clone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class notificationEmail {
    private String subject;
    private String recipient;
    private String body;
}
