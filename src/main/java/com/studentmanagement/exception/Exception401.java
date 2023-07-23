package com.studentmanagement.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }
}
