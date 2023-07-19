package com.studentmanagement.repository.security;

import com.studentmanagement.entity.security.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findOneById(Long id);
}
