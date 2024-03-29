package ru.miroshka.astra4backclient.exceptios;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.miroshka.astra4backclient.exceptios.errors.FieldsValidationError;
import ru.miroshka.astra4backclient.exceptios.errors.GetProcessServerException;
import ru.miroshka.astra4backclient.exceptios.errors.ResourceNotFoundException;
import ru.miroshka.astra4backclient.exceptios.errors.ValidationException;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<FieldsValidationError> catchValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new FieldsValidationError(e.getErrorFieldsMessages()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<AppError> catchGetProcessServerException(GetProcessServerException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()), HttpStatus.NOT_FOUND);
    }

    //@ExceptionHandler
}
