package com.studentmanagement.repository;

import com.studentmanagement.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    List<StudentEntity> findAll();

    StudentEntity findOneById(Long id);

    List<StudentEntity> findByFirstNameContainingIgnoreCase(String name);

    List<StudentEntity> findByPhoneNumberContaining(String name);

    StudentEntity findByCode(Long code);
}
