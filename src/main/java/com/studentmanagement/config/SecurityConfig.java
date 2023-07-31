package com.studentmanagement.config;


import com.studentmanagement.jwt.JwtAuthenticationFilter;
import com.studentmanagement.service.impl.CustomUserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailServiceImpl userDetailServiceImpl;

    public SecurityConfig(CustomUserDetailServiceImpl userDetailServiceImpl) {
        this.userDetailServiceImpl = userDetailServiceImpl;
    }

    //Config authorization and authentication
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/signup").permitAll()
                                .requestMatchers("/signup/**").permitAll()
//                                .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                                .requestMatchers("/admin/**").permitAll()
                                .requestMatchers("/verify").permitAll()
                                .requestMatchers("/profile").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/student").hasAnyAuthority("ADMIN", "MANAGER", "USER")
                                .requestMatchers(HttpMethod.GET, "/api/student/**").hasAnyAuthority("ADMIN", "MANAGER")
                                .requestMatchers(HttpMethod.POST, "/api/student").hasAnyAuthority("ADMIN", "MANAGER", "USER")
                                .requestMatchers(HttpMethod.POST, "/api/student/**").hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/student/**").hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/student/**").hasAnyAuthority("ADMIN", "MANAGER")
                                .requestMatchers(HttpMethod.DELETE, "/api/student").hasAnyAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/user/**").hasAnyAuthority("MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .anyRequest().authenticated()
                )
                .logout(LogoutConfigurer::permitAll)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(userDetailServiceImpl);
        http.addFilterBefore(jwtAuthenticationFilterr(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilterr() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
