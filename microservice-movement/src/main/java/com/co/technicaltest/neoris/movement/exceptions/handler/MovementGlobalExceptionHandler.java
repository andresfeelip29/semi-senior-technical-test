package com.co.technicaltest.neoris.movement.exceptions.handler;

import com.co.technicaltest.neoris.movement.exceptions.BalanceUpdateException;
import domain.exception.DomainException;
import handler.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class MovementGlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {DomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ServerResponse> handleException(DomainException exception) {
        log.error(exception.getMessage(), exception);
        return this.buildErrorResponse(exception, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ServerResponse> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return this.buildErrorResponse(exception, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public Mono<ServerResponse> handleException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        List<String> errs = this.fieldValidate(e);
        return this.buildErrorResponse(e, String.join(", ", errs), HttpStatus.PRECONDITION_FAILED);
    }

    @ResponseBody
    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ServerResponse> handleException(ValidationException validationException) {
        Mono<ServerResponse> errorResponse;
        if (validationException instanceof ConstraintViolationException) {
            String violations = extractViolationsFromException((ConstraintViolationException) validationException);
            log.error(violations, validationException);
            errorResponse = this.buildErrorResponse(validationException,
                    validationException.getMessage(),
                    HttpStatus.BAD_REQUEST);
        } else {
            String exceptionMessage = validationException.getMessage();
            log.error(exceptionMessage, validationException);
            errorResponse = this.buildErrorResponse(validationException,
                    validationException.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        return errorResponse;
    }

    private String extractViolationsFromException(ConstraintViolationException validationException) {
        return validationException.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("--"));
    }


    @ResponseBody
    @ExceptionHandler(value = {BalanceUpdateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ServerResponse> handleException(Throwable e) {
        log.error(e.getMessage(), e);
        return this.buildErrorResponse(e, e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    private Mono<ServerResponse> buildErrorResponse(Throwable e, String message, HttpStatus httpStatus) {
        log.error(e.getMessage());
        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(ErrorResponse.builder()
                        .code(httpStatus.value())
                        .message(message)
                        .build()));
    }

    private List<String> fieldValidate(MethodArgumentNotValidException result) {
        return result.getBindingResult()
                .getAllErrors()
                .stream()
                .map(err -> ((FieldError) err).getField().concat(" ").concat(Objects.requireNonNull(err.getDefaultMessage())))
                .toList();
    }
}
