package com.studentmanagement.service.impl;

import com.studentmanagement.entity.security.UserEntity;
import com.studentmanagement.exception.Exception404;
import com.studentmanagement.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {
    private AccountService accountService;

    @Autowired
    public CustomUserDetailServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = accountService.loadUserByUsername(username);
        if (userEntity == null) throw new BadCredentialsException(String.format("User %s not found", username));
        List<SimpleGrantedAuthority> authorities = userEntity.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
        UserDetails userDetails = User
                .withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(authorities)
                .build();
        return userDetails;
    }
}
