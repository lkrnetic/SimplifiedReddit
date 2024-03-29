package com.example.SimplifiedReddit.model;

import com.example.SimplifiedReddit.model.enums.VoteType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private Integer voteCount;

    @JsonBackReference(value="userThatCreatedPost")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    @JsonBackReference(value="subredditPosts")
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "subreddit_id"
    )
    private Subreddit subreddit;

    @JsonManagedReference(value="postComments")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Integer updateVoteCount(Post post, Vote vote) {
        if (vote.getVoteType() == VoteType.UPVOTE) {
            post.setVoteCount(post.getVoteCount() + 1);
        }
        else { post.setVoteCount(post.getVoteCount() - 1); }

        return post.getVoteCount();
    }
}
