package com.studentmanagement.repository.security;

import com.studentmanagement.entity.security.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    RoleEntity findByName(String roleName);
}
