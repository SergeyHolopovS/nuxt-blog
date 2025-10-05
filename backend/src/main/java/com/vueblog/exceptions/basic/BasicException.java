package com.vueblog.exceptions.basic;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BasicException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public BasicException(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

}
