package com.example.SimplifiedReddit.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.List;


@Table(name="subreddit")
@Data
@NoArgsConstructor
@Entity
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Subreddit name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @OneToMany(mappedBy = "subreddit", cascade = CascadeType.ALL)
    private List<Post> posts;
    @ManyToOne
    private User user;

}
