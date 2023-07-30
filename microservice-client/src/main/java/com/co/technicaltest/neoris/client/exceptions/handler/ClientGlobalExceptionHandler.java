package com.co.technicaltest.neoris.client.exceptions.handler;

import com.co.technicaltest.neoris.client.exceptions.ClientNotFoundException;
import handler.ErrorResponse;
import handler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestControllerAdvice
public class ClientGlobalExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {ClientNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleException(Throwable e) {
        log.error(e.getMessage(), e);
        return this.buildErrorResponse(e, e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
