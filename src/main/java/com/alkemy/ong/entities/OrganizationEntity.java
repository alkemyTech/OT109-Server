package com.alkemy.ong.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "organizations")
@Getter
@Setter
@SQLDelete(sql = "UPDATE organizations SET deleted_at = now() WHERE organization_id=?")
@Where(clause = "deleted_at IS NULL")
public class OrganizationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    private String address;

    private Integer phone;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(columnDefinition = "TEXT", name = "welcome_text", nullable = false)
    private String welcomeText;

    @Column(columnDefinition = "TEXT", name = "about_us_text")
    private String aboutUsText;

    @JsonBackReference
    @OneToMany(mappedBy = "organization", fetch = FetchType.EAGER)
    private List<Member> members;

    @JsonIgnore
    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @JsonIgnore
    @Temporal(value = TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(insertable = false, name = "updated_at")
    private Date updatedAt;

    @JsonIgnore
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    @OneToMany(targetEntity = Slide.class,mappedBy = "organization",cascade = {CascadeType.ALL, CascadeType.MERGE} ,fetch = FetchType.LAZY)
    private List<Slide> slide;

}
