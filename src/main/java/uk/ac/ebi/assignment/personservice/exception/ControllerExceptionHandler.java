package uk.ac.ebi.assignment.personservice.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { PersonNotFoundException.class })
    ResponseEntity<Object> validationExceptions(PersonNotFoundException ex) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("timestamp", String.valueOf(new Date()));
        responseBody.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        responseBody.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }
}
