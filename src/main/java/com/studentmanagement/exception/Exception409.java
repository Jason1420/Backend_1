package com.studentmanagement.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Exception409 extends RuntimeException{
    public Exception409(String message) {
        super(message);
    }
}
