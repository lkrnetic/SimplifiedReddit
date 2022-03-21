package com.example.SimplifiedReddit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="post")
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

    public Post(String title, String text, User user) {
        this.title = title;
        this.text = text;
        this.user = user;
    }



}
