package com.basirhat.examservice.exceptionhadler;

import com.basirhat.examservice.model.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllException(Exception exception) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(exception.getMessage());
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


    @ExceptionHandler({IllegalArgumentException.class})
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException exception) {
        final var validationProblemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "validation error");
        validationProblemDetail.setProperty("errors", List.of(ConstraintViolation.builder().message(exception.getMessage()).build()));
        return validationProblemDetail;
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ProblemDetail handleMethodNotValidException(ConstraintViolationException exception) {
        final var validationProblemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "validation error");
        final var errors = exception.getConstraintViolations().stream().map(violation -> ConstraintViolation.builder()
                        .message(violation.getMessage())
                        .rejectedValue(Objects.isNull(violation.getInvalidValue()) ? "null" : violation.getInvalidValue().toString())
                        .build())
                .toList();
        validationProblemDetail.setProperty("errors", errors);
        return validationProblemDetail;
    }
}
