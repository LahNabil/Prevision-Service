package net.lahlalia.previsions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PrevisionNotFoundException extends RuntimeException{
    public PrevisionNotFoundException(String message) {
        super(message);
    }
}
