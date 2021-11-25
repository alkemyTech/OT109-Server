package com.alkemy.ong.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    @Nullable
    private String description;
    @Nullable
    private String image;
    @Nullable
    private Date createdAt;
    @Nullable
    private Date updatedAt;
    @Nullable
    private Date deletedAt;

}
