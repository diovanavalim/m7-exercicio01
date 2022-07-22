package dh.meli.joalheria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class JoiaAlreadyExists extends RuntimeException{
    public JoiaAlreadyExists(String message) {
        super(message);
    }
}
