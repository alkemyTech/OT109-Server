package com.alkemy.ong.entities;

import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotEmpty(message = "Name may not be empty")
    @Size(min = 3)
    @Column(nullable = false)
    private String name;
    @Column(nullable = true)
    private String description;
    @Column(nullable = true)
    private String image;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

}
