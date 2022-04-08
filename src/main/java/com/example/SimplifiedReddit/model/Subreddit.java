package com.example.SimplifiedReddit.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;
import java.util.Set;


@Table(name="subreddit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Subreddit name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @JsonManagedReference(value="subredditPosts")
    @OneToMany(mappedBy = "subreddit", cascade = CascadeType.ALL)
    private List<Post> posts;

    @JsonBackReference(value="userThatCreatedSubreddit")
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @ManyToMany(mappedBy = "followedSubreddits")
    Set<User> followers;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subreddit subreddit = (Subreddit) o;
        return id.equals(subreddit.id);
    }
}
