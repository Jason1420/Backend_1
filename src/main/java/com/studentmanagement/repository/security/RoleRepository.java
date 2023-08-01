package com.studentmanagement.repository.security;

import com.studentmanagement.entity.security.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    RoleEntity findByName(String roleName);

    Set<RoleEntity> findOneByName(String roleName);
}
