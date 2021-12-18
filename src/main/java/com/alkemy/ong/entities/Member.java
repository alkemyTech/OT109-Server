package com.alkemy.ong.entities;

import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
@SQLDelete(sql = "UPDATE members SET deleted_at = now() WHERE member_id=?")
@Where(clause = "deleted_at IS NULL")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Setter(AccessLevel.NONE)
    @Column(name = "member_id" ,nullable = false, unique = true)
    private Long Id;

    @NotBlank(message = "Member name can't be null,empty or whitespace.")
    @Pattern(regexp="[^0-9]*$",message = "Member name must have only letters")
    @Column(nullable = false)
    private String name;
    @Nullable
    private String facebookUrl;
    @Nullable
    private String instagramUrl;
    @Nullable
    private String linkedinUrl;
    @Column(nullable = false)
    private String image;
    @NotNull
    private String description;

    @Valid
    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    public Member(Long id, String name, @Nullable String facebookUrl, @Nullable String instagramUrl, @Nullable String linkedinUrl, String image, String description, OrganizationEntity organization, Date createdAt) {
        Id = id;
        this.name = name;
        this.facebookUrl = facebookUrl;
        this.instagramUrl = instagramUrl;
        this.linkedinUrl = linkedinUrl;
        this.image = image;
        this.description = description;
        this.organization = organization;
        this.createdAt = createdAt;
    }
}
