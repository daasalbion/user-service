package py.com.daas.userservice.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.annotation.JsonProperty;

import py.com.daas.userservice.exceptions.EmailExistsException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + " " + e.getDefaultMessage())
                .toList();
        return new ResponseEntity<>(buildErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleParameterNotReadable(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailExistsException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> defaultHandler(Exception ex) {
        return new ResponseEntity<>(buildErrorResponse(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public record ErrorResponse(@JsonProperty("mensaje") String message,
                                @JsonProperty("detalles") List<String> details) {
        public ErrorResponse(String message) {
            this(message, List.of());
        }
    }

    private ErrorResponse buildErrorResponse(List<String> errors) {
        return new ErrorResponse("La petición contiene errores", errors);
    }

    private ErrorResponse buildErrorResponse(Exception ex) {
        var details = Optional.ofNullable(ex.getCause())
                .stream()
                .map(Throwable::getMessage)
                .toList();
        return new ErrorResponse(ex.getMessage(), details);
    }

}