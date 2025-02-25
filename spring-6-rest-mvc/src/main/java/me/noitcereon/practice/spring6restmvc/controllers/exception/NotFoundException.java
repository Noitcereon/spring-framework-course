package me.noitcereon.practice.spring6restmvc.controllers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception to be thrown on a NOT FOUND response.
 *
 * @apiNote Alternatives to exception handling apart from {@link ResponseStatus} in Spring are: <br>
 * {@link org.springframework.web.bind.annotation.ExceptionHandler} (annotated on methods) and <br>
 * {@link org.springframework.web.bind.annotation.ControllerAdvice} (targets Class and is global exception handling)
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // if NotFoundException is thrown in a controller it will return notFound response.
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
