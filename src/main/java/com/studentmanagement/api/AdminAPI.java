package com.studentmanagement.api;

import com.studentmanagement.dto.security.UserDTO;
import com.studentmanagement.entity.security.RoleEntity;
import com.studentmanagement.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminAPI {
    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }
    @GetMapping("/admin/{id}")
    public UserDTO showUser(@PathVariable("id") Long id){
        return accountService.showUser(id);
    }

    @PostMapping("/admin")
    public String addRole(@RequestBody String role){
        return accountService.addNewRole(role);
    }

    @PutMapping ("/admin/{id}")
    public String updateRoleToUser(@RequestBody List<RoleEntity> roles, @PathVariable("id") Long id ){
        return accountService.updateRole(roles, id);
    }
    @DeleteMapping("/admin")
    public String deleteUser(@RequestBody Long[] ids){
        return accountService.deleteUser(ids);
    }
}
