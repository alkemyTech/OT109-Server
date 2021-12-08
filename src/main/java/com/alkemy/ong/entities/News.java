package com.alkemy.ong.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity @NoArgsConstructor
@Table(name = "news")
@SQLDelete(sql = "UPDATE news SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at IS NULL")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String image;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "categories_id", nullable = false)
    private Category category;

    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

}
