package com.vueblog.exceptions.writers;

import com.vueblog.exceptions.basic.BasicException;
import org.springframework.http.HttpStatus;

public class WriterNotFoundException extends BasicException  {
    private static final String DEFAULT_MESSAGE = "Writer not found.";

    public WriterNotFoundException() {
        super(HttpStatus.BAD_REQUEST, DEFAULT_MESSAGE);
    }

    public WriterNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
