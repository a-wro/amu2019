package com.aleksy.springrest.library.utils;

import com.aleksy.springrest.library.exceptions.AuthorNotFoundException;
import com.aleksy.springrest.library.exceptions.BookNotFoundException;
import com.aleksy.springrest.library.exceptions.BookPreviewDisabledException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookPreviewDisabledException.class)
    public ResponseEntity<String> handleBookPreview(Exception e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Preview unavailable");
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<String> handleAuthorNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect author ID");
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFound(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect book ID");
    }

}
