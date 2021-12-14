package com.alkemy.ong.dtos.responses;

import com.alkemy.ong.entities.TestimonialEntity;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class TestimonialListDTO {

    @NotNull
    @Min(value = 1, message = "Id cannot be less than one.")
    private Long id;
    @NotBlank
    @Min(value = 3, message = "Name should have more than three letters")
    private String name;
    //en la base de datos figura default null
    private String content;
    private Date createdAt;


}
