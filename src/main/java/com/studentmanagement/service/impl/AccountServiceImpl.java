package com.studentmanagement.service.impl;

import com.studentmanagement.converter.UserConverter;
import com.studentmanagement.dto.security.UserDTO;
import com.studentmanagement.entity.security.RoleEntity;
import com.studentmanagement.entity.security.UserEntity;
import com.studentmanagement.exception.Exception404;
import com.studentmanagement.exception.Exception409;
import com.studentmanagement.repository.security.RoleRepository;
import com.studentmanagement.repository.security.UserRepository;
import com.studentmanagement.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserConverter userConverter;

    @Autowired
    public AccountServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userConverter = userConverter;
    }

    //    @Override
    public UserEntity addNewUser(String username, String password, String email, String confirmPassword) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) throw new RuntimeException("This user already exist");
        if(!password.equals(confirmPassword)) throw new RuntimeException("Password not match");
        userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        UserEntity savedUser = userRepository.save(userEntity);
        return savedUser;
    }

    @Override
    public String addNewUser(UserDTO dto) {
        UserEntity entity = userRepository.findByUsername(dto.getUsername());
        if(entity != null){
            throw new Exception409("This user already exits");
        }
        if(dto.getId() != null){
            UserEntity oldEntity = userRepository.findOneById(dto.getId());
            UserEntity newEntity = userConverter.toEntity(dto, oldEntity);
            newEntity.setPassword(passwordEncoder.encode(newEntity.getPassword()));
            userRepository.save(newEntity);
            return "Successfully update user with id: " +newEntity.getId();
        }
        UserEntity newEntity = userConverter.toEntity(dto);
        newEntity.setPassword(passwordEncoder.encode(newEntity.getPassword()));
        RoleEntity roleUser = new RoleEntity("USER");
        List<RoleEntity> listEntity = new ArrayList<>();
        listEntity.add(roleUser);
        newEntity.setRoles(listEntity);
        userRepository.save(newEntity);
        return "Successfully create an user with id :" + newEntity.getId();
    }

    @Override
    public String addNewRole(String roleName) {
        RoleEntity entity = roleRepository.findByName(roleName);
        if(entity != null){
            return "This role already exist";
        }
        RoleEntity NewEntity = new RoleEntity(roleName);
        roleRepository.save(NewEntity);
        return "role name "+roleName +" was added";
    }

    @Override
    public void addRoleToUser(String username, String role) {
        UserEntity user = userRepository.findByUsername(username);

    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        UserEntity userEntity = userRepository.findByUsername(username);
        RoleEntity roleEntity = roleRepository.findById(role).get();
        userEntity.getRoles().remove(roleEntity);
    }

    @Override
    public UserEntity loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String updateRole(List<RoleEntity> roles, Long id) {
        UserEntity entity = userRepository.findOneById(id);
        entity.setRoles(roles);
        userRepository.save(entity);
        return "successfully update role";
    }

    @Override
    public String deleteUser(Long[] ids) {
        for(Long id:ids){
            userRepository.deleteById(id);
        }
        return "Successfully delete user";
    }

    @Override
    public UserDTO showUser(Long id) {
        UserEntity entity = userRepository.findOneById(id);
        if(entity != null){
            UserDTO dto = userConverter.toDTO(entity);
            return dto;
        }
        throw new Exception404("NOT FOUND USER");
    }



}
