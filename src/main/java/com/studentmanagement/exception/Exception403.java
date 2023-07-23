package com.studentmanagement.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Exception403 extends RuntimeException {
    public Exception403(String message) {
        super(message);
    }
}
