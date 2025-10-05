package com.vueblog.exceptions;

import com.vueblog.exceptions.basic.BasicException;
import com.vueblog.exceptions.dto.response.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BasicException.class)
    public ResponseEntity<ErrorDto> basicExceptionHandler(BasicException exception) {
        return new ResponseEntity<>(
                ErrorDto.builder()
                    .status(exception.getStatus())
                    .message(exception.getMessage())
                    .build(),
                exception.getStatus()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleMethodArgumentNotValidException() {
        return ErrorDto
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Invalid request fields")
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto globalExceptionHandling(Exception exception) {
        log.info(exception.getMessage());
        return ErrorDto
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Internal server error.")
                .build();
    }

}
