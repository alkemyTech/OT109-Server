package com.alkemy.ong.entities;

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
    private int orderNum;
    @ManyToOne
    @JoinColumn(name = "organization_ID")
    private OrganizationEntity organization;

    public Slide(String imageUrl, String text, int orderNum, OrganizationEntity organization) {
        this.imageUrl = imageUrl;
        this.text = text;
        this.orderNum = orderNum;
        this.organization = organization;
    }
}
