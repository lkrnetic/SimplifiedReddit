package com.example.SimplifiedReddit.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Table(name="comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String text;

    @JsonBackReference(value="postComments")
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonBackReference(value="userThatCreatedComment")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
