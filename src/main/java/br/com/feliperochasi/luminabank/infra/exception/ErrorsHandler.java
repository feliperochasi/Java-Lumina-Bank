package br.com.feliperochasi.luminabank.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ErrorsHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> errorHandler404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> errorHandlerUrl404() {
        return ResponseEntity.notFound().build();
    }
}
