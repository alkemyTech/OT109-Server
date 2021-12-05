package com.alkemy.ong.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "slides")
public class Slide {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String imageUrl;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false, unique = true)
    private Integer orderNum;
    @JsonIgnoreProperties(value = {"slide"})
    @ManyToOne
    private OrganizationEntity organization;
}
