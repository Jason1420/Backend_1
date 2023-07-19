package com.studentmanagement.dto.security;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private List<RoleDTO> roles;
}
