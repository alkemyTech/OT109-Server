package com.alkemy.ong.pojos;

import com.alkemy.ong.entities.Role;
import com.alkemy.ong.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private Role role;

    public ListUserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.photo = user.getPhoto();
        this.role = user.getRole();
    }
}
