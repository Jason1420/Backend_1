package com.studentmanagement.service;

import com.studentmanagement.dto.security.UserDTO;
import com.studentmanagement.entity.security.RoleEntity;
import com.studentmanagement.entity.security.UserEntity;

import java.util.List;

public interface AccountService {
    String addNewUser(UserDTO userDTO);

    String addNewRole(String name);

    UserEntity loadUserByUsername(String username);

    String updateRole(String roleName, Long userId);

    String deleteUser(Long[] ids);

    UserDTO showUser(Long id);

    String confirmToken(String token);


}
