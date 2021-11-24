package com.alkemy.ong.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "contacts")
@SQLDelete(sql = "UPDATE posts SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at = null")
public class Contact implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    private String phone;

    @NonNull
    private String email;

    @NonNull
    private String message;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name="fecha_creacion")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

}
