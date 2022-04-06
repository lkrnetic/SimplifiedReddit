package com.example.SimplifiedReddit.model;

import com.example.SimplifiedReddit.model.enums.VoteType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name="vote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    @JsonBackReference(value="voteCreatedInPost")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonBackReference(value="voteCreatedByUser")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isEdited = false;

}
