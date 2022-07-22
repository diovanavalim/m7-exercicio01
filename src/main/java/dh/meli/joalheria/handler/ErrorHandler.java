package dh.meli.joalheria.handler;

import dh.meli.joalheria.exception.ExceptionDetails;
import dh.meli.joalheria.exception.InternalServerError;
import dh.meli.joalheria.exception.JoiaAlreadyExists;
import dh.meli.joalheria.exception.JoiaNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ExceptionDetails> internalServerErrorHandler(InternalServerError ex) {
        return new ResponseEntity<ExceptionDetails>(ExceptionDetails
                .builder()
                .title("Internal Server Error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JoiaNotFound.class)
    public ResponseEntity<ExceptionDetails> joiaNotFoundHandler(JoiaNotFound ex) {
        return new ResponseEntity<ExceptionDetails>(ExceptionDetails
                .builder()
                .title("Resource Not Found")
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JoiaAlreadyExists.class)
    public ResponseEntity<ExceptionDetails> joiaAlreadyExistsHandler(JoiaAlreadyExists ex) {
        return new ResponseEntity<ExceptionDetails>(ExceptionDetails
                .builder()
                .title("Resource Already Exists")
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build(),
                HttpStatus.CONFLICT);
    }
}
