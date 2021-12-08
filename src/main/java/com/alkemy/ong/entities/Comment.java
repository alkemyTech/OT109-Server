package com.alkemy.ong.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comments_id", nullable = false)
    private long id;

    @Column(name = "body")
    private String body;

    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @JoinColumn(name = "news_id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private News news;

}
