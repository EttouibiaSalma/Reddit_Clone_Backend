package com.reddit.reddit_clone.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor
@Entity
@Table(name = "token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private Instant expiryDate;
    //whenever a user is registered we generate a token and store it in the database
    // through this entity and send it as part of the activation link to the user, whenever
    //the link is clicked we lookup the user associated with this token and enabled it
}
