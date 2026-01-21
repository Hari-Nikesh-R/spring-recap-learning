package org.example.javafullstacktraining.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHanler {
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleResourceNotFoundException(NullPointerException ex) {
        return new ResponseEntity<>("New", HttpStatus.BAD_GATEWAY);
    }
}
