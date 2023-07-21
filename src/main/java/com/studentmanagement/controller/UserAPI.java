package com.studentmanagement.controller;

import com.studentmanagement.dto.security.AuthResponseDTO;
import com.studentmanagement.dto.security.LoginDTO;
import com.studentmanagement.dto.security.UserDTO;
import com.studentmanagement.jwt.JwtGenerator;
import com.studentmanagement.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserAPI {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping("/signup")
    public String signUpUser(@RequestBody @Valid UserDTO userDTO) {
        return accountService.addNewUser(userDTO);
    }
    @GetMapping(path = "/signup/confirm")
    public String confirm(@RequestParam("token") String token) {
        return accountService.confirmToken(token);
    }


    @PostMapping("/user/{id}")
    public String updateUser(@RequestBody @Valid UserDTO userDTO, @PathVariable("id") Long id) {
        userDTO.setId(id);
        return accountService.addNewUser(userDTO);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return  new AuthResponseDTO(token).toString();
    }

}
