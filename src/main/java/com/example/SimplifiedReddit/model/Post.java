package com.example.SimplifiedReddit.model;

import lombok.*;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Table(name="post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String text;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
