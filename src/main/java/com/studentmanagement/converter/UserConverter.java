package com.studentmanagement.converter;

import com.studentmanagement.dto.security.UserDTO;
import com.studentmanagement.entity.security.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDTO toDTO(UserEntity entity) {
        UserDTO dto = new UserDTO();
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        return dto;
    }

    public UserEntity toEntity(UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        return entity;
    }

    public UserEntity toEntity(UserDTO dto, UserEntity oldEntity) {
        oldEntity.setUsername(dto.getUsername());
        oldEntity.setPassword(dto.getPassword());
        oldEntity.setEmail(dto.getEmail());
        return oldEntity;
    }
}
