package com.alkemy.ong.entities;

import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "contacts")
@SQLDelete(sql = "UPDATE posts SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at = null")
public class Slide {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String imageUrl;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private String order;
    @ManyToOne
    @JoinColumn(name = "organization_ID")
    private OrganizationEntity organization;

}
