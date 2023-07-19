package com.studentmanagement.api;

import com.studentmanagement.dto.security.AuthResponseDTO;
import com.studentmanagement.dto.security.LoginDTO;
import com.studentmanagement.dto.security.UserDTO;
import com.studentmanagement.jwt.JwtGenerator;
import com.studentmanagement.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAPI {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping("/signup")
    public String signUpUser(@RequestBody UserDTO userDTO) {
        return accountService.addNewUser(userDTO);
    }

    @PostMapping("/user/{id}")
    public String updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") Long id) {
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
