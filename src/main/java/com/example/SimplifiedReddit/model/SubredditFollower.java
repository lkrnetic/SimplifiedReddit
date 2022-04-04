package com.example.SimplifiedReddit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Table(name="subreddit_follower")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SubredditFollower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "subreddit_id"
    )
    private Subreddit subreddit;

}
