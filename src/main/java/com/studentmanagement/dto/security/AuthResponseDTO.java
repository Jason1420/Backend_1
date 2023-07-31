package com.studentmanagement.dto.security;

public class AuthResponseDTO {
    private final String accessToken;
    private final String tokenType = "Bearer ";

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "AuthResponseDTO{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }
}
