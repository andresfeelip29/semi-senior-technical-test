package com.co.technicaltest.neoris.account.exceptions.handler;

import domain.exception.client.ClientAccountNotFoundException;
import handler.ErrorResponse;
import handler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AccountGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {ClientAccountNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleException(Throwable e) {
        log.error(e.getMessage(), e);
        return this.buildErrorResponse(e, e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
