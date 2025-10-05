package com.vueblog.exceptions.posts;

import com.vueblog.exceptions.basic.BasicException;
import org.springframework.http.HttpStatus;

public class PostNotFoundException extends BasicException {

    private static final HttpStatus DEFAULT_STATUS = HttpStatus.BAD_REQUEST;
    private static final String DEFAULT_MESSAGE = "Post not found.";

    public PostNotFoundException() {
        super(DEFAULT_STATUS, DEFAULT_MESSAGE);
    }

    public PostNotFoundException(HttpStatus status, String message) {
        super(status, message);
    }

}
