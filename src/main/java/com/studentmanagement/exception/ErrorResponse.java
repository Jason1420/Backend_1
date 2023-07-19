package com.studentmanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Data @AllArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private String message;
}
