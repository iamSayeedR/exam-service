package com.basirhat.examservice.exceptionhadler;

import com.basirhat.examservice.model.ConstraintViolation;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllException(Exception exception) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("message");
    }

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ErrorResponse handleIllegalArgumentException(Exception exception) {
        return ErrorResponse.builder(exception, BAD_REQUEST, exception.getMessage()).build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ProblemDetail handleMethodNotValidException(MethodArgumentNotValidException exception) {
        final var validationProblemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "validation error");
        final var errors = exception.getFieldErrors().stream().map(violation -> ConstraintViolation.builder()
                        .message(violation.getDefaultMessage())
                        .fieldName(violation.getField()).rejectedValue(Objects.isNull(violation.getRejectedValue()) ? "null" : violation.getRejectedValue().toString())
                        .build())
                .toList();
        validationProblemDetail.setProperty("errors", errors);
        return validationProblemDetail;
    }
}
