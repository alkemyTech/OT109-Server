package com.alkemy.ong.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@SQLDelete(sql = "UPDATE table_product SET deletedAt = now() WHERE id=?")
@Where(clause = "deleted=null")
public class User implements Serializable{
    
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NonNull
    @Column(nullable = false)
    private String firstName;
    
    @NonNull
    @Column(nullable = false)
    private String lastName;
    
    @NonNull
    @Column(nullable = false)
    @Email
    private String email;
    
    @NonNull
    @Column(nullable = false)
    private String password;
    
    private String photo;
    
    @ManyToOne
    @NonNull
    private Role role;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date deletedAt;
    
}
