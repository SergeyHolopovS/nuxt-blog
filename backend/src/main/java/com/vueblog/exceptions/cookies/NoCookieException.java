package com.vueblog.exceptions.cookies;

import com.vueblog.exceptions.basic.BasicException;
import org.springframework.http.HttpStatus;

public class NoCookieException extends BasicException {

    private final static String DEFAULT_MESSAGE = "No cookie found.";

    public NoCookieException() {
        super(HttpStatus.BAD_REQUEST, DEFAULT_MESSAGE);
    }

    public NoCookieException(HttpStatus status, String message) {
        super(status, message);
    }

}
