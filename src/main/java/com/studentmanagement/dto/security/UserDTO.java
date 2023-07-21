package com.studentmanagement.dto.security;

import com.studentmanagement.entity.security.RoleEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    @NotBlank(message = "User name shouldn't be blank")
    @Pattern(regexp = "^[a-zA-z][^\\W_]{7,14}$",
            message = "username must be 8-15 characters and must start with a letter")
    private String username;
    @NotBlank(message = "User name shouldn't be blank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "password minimum eight characters, at least one uppercase letter," +
                    " one lowercase letter, one number and one special character")
    private String password;
    @Email(message = "Invalid Email")
    private String email;
    private List<RoleDTO> roles;
}
