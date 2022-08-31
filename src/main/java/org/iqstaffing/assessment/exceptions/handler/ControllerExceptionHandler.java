package org.iqstaffing.assessment.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.iqstaffing.assessment.exceptions.CategoryNotFoundException;
import org.iqstaffing.assessment.exceptions.IndexException;
import org.iqstaffing.assessment.exceptions.IngredientNotFoundException;
import org.iqstaffing.assessment.exceptions.InstructionNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleBadRequest(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({InstructionNotFoundException.class, IngredientNotFoundException.class, CategoryNotFoundException.class})
    public ResponseEntity<Object> handleNotFound(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({IndexException.class})
    public ResponseEntity<Object> handleServerError(RuntimeException exception, WebRequest request) {
        return handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        headers.add("Content-Type", "application/problem+json");

        ControllerExceptionResponse messageBody = new ControllerExceptionResponse(exception.getMessage());

        return super.handleExceptionInternal(exception, messageBody, headers, status, request);
    }
}
