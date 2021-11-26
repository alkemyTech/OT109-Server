package com.alkemy.ong.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
@SQLDelete(sql = "UPDATE members SET deleted_at = now() WHERE member_id=?")
@Where(clause = "deleted_at=null")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "member_id" ,nullable = false, unique = true)
    private Long Id;

    @NotNull
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id", referencedColumnName = "organization_id")
    private OrganizationEntity organization;

    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
}
