package com.annieryannel.recommendationsapp.controllers;

import com.annieryannel.recommendationsapp.DTO.ErrorResponse;
import com.annieryannel.recommendationsapp.exceptions.LikesConflictException;
import com.annieryannel.recommendationsapp.exceptions.UserAlreadyExistsException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionController {

    private static final String FORM_ERROR = "FORM_ERROR";
    private static final String IMPOSSIBLE_REQUEST = "IMPOSSIBLE_REQUEST";
    private static final String DATA_CONFLICT = "DATA_CONFLICT";
    private static final String PERMISSION_ERROR = "PERMISSION_ERROR";

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowedException(MethodNotAllowedException exc) {
        ErrorResponse response = new ErrorResponse(PERMISSION_ERROR, List.of(Objects.requireNonNull(exc.getMessage())));
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exc) {
        List<String> details = exc.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        ErrorResponse response = new ErrorResponse(FORM_ERROR, details);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(LikesConflictException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(LikesConflictException exc) {
        ErrorResponse response = new ErrorResponse(DATA_CONFLICT, List.of(exc.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistException(UserAlreadyExistsException exc) {
        ErrorResponse response = new ErrorResponse(DATA_CONFLICT, List.of(exc.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NoSuchElementException exc) {
        ErrorResponse response = new ErrorResponse(IMPOSSIBLE_REQUEST, List.of(exc.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}