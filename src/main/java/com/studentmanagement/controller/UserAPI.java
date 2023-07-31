package com.studentmanagement.controller;

import com.studentmanagement.dto.security.AuthResponseDTO;
import com.studentmanagement.dto.security.LoginDTO;
import com.studentmanagement.dto.security.UserDTO;
import com.studentmanagement.email.EmailSender;
import com.studentmanagement.email.token.ConfirmationToken;
import com.studentmanagement.email.token.ConfirmationTokenService;
import com.studentmanagement.entity.security.UserEntity;
import com.studentmanagement.jwt.JwtGenerator;
import com.studentmanagement.repository.security.UserRepository;
import com.studentmanagement.service.impl.AccountServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class UserAPI {

    private final AccountServiceImpl accountService;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final ConfirmationTokenService confirmationTokenService;

    public UserAPI(AccountServiceImpl accountService, AuthenticationManager authenticationManager,
                   JwtGenerator jwtGenerator, UserRepository userRepository, EmailSender emailSender,
                   ConfirmationTokenService confirmationTokenService) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userRepository = userRepository;
        this.emailSender = emailSender;
        this.confirmationTokenService = confirmationTokenService;
    }

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
        return new AuthResponseDTO(token).toString();
//        return null;
    }

    @PostMapping("/verify")
    public String verify(@RequestBody LoginDTO loginDTO) {
        UserEntity userEntity = userRepository.findByUsername(loginDTO.getUsername());
        if (userEntity.isEnabled()) {
            return "Account had verified";
        } else {
            String token = UUID.randomUUID().toString();

            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    userEntity
            );

            confirmationTokenService.saveConfirmationToken(
                    confirmationToken);
            String link = "http://localhost:8080/signup/confirm?token=" + token;
            emailSender.send(
                    userEntity.getEmail(),
                    accountService.buildEmail(userEntity.getUsername(), link));
        }
        return "Please check your email and click the link to verify your account!";
    }

}
