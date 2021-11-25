package com.alkemy.ong.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@SQLDelete(sql = "UPDATE organizations SET deleted_at = current_timestamp() WHERE id = ?")
@Where(clause = "deleted_at = null")

public class OrganizationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Temporal(value = TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "created_at")
	private Date createdAt;

	@Temporal(value = TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "deleted_at")
	private Date deletedAt;
}
