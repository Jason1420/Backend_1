package com.studentmanagement.repository.security;

import com.studentmanagement.entity.security.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    UserEntity findOneById(Long id);


    @Transactional
    @Modifying
    @Query("UPDATE UserEntity a " +
            "SET a.enabled = TRUE WHERE a.username = ?1")
    int enableAppUser(String username);
}
