package com.studentmanagement.converter;

import com.studentmanagement.dto.Gender;
import com.studentmanagement.dto.StudentDTO;
import com.studentmanagement.entity.StudentEntity;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class StudentConverter {
    public StudentDTO toDTO(StudentEntity entity){
        StudentDTO studentDTO = new StudentDTO();
        if(entity.getId() != null){
            studentDTO.setId(entity.getId());
        }
        studentDTO.setCode(entity.getCode());
        studentDTO.setFirstName(entity.getFirstName());
        studentDTO.setLastName(entity.getLastName());
        studentDTO.setDateOfBirth(entity.getDateOfBirth().toString().substring(0,10));
        studentDTO.setGender(entity.getGender().toString());
        studentDTO.setAddress(entity.getAddress());
        studentDTO.setDepartment(entity.getDepartment());
        studentDTO.setEmail(entity.getEmail());
        studentDTO.setPhoneNumber(entity.getPhoneNumber());
        return studentDTO;
    }

    public StudentEntity toEntity(StudentDTO dto){
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setCode(dto.getCode());
        studentEntity.setFirstName(dto.getFirstName());
        studentEntity.setLastName(dto.getLastName());
        studentEntity.setDateOfBirth(Date.valueOf(dto.getDateOfBirth()));
        studentEntity.setGender(Gender.valueOf(dto.getGender()));
        studentEntity.setAddress(dto.getAddress());
        studentEntity.setDepartment(dto.getDepartment());
        studentEntity.setEmail(dto.getEmail());
        studentEntity.setPhoneNumber(dto.getPhoneNumber());
        return studentEntity;
    }

    public StudentEntity toEntity(StudentDTO dto, StudentEntity oldEntity) {
        oldEntity.setCode(dto.getCode());
        oldEntity.setFirstName(dto.getFirstName());
        oldEntity.setLastName(dto.getLastName());
        oldEntity.setDateOfBirth(Date.valueOf(dto.getDateOfBirth()));
        oldEntity.setGender(Gender.valueOf(dto.getGender()));
        oldEntity.setAddress(dto.getAddress());
        oldEntity.setDepartment(dto.getDepartment());
        oldEntity.setEmail(dto.getEmail());
        oldEntity.setPhoneNumber(dto.getPhoneNumber());
        return oldEntity;
    }
}
