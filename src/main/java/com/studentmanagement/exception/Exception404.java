package com.studentmanagement.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Exception404 extends RuntimeException {
    public Exception404(String message) {
        super(message);
    }
}
