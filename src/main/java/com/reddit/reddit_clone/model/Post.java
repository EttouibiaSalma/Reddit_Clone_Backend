package com.reddit.reddit_clone.model;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @NotBlank( message = "Post name can't be empty or Null")
    private String postName;
    @Nullable
    private String url;
    @Lob
    private String description;
    private Integer voteCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private subReddit subReddit;
}
