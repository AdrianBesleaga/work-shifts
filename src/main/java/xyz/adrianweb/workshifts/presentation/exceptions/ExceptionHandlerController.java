package xyz.adrianweb.workshifts.presentation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import xyz.adrianweb.workshifts.core.validation.exceptions.ValidationException;

import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> badRequestHandler(ValidationException ex) {
        return Map.of("error", ex.getMessage());
    }
}
