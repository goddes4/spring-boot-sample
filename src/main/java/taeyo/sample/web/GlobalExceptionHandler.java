package taeyo.sample.web;

import taeyo.sample.exception.BasicErrorResponse;
import taeyo.sample.exception.AccountNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by tykim on 2015-05-29.
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicErrorResponse handleAccountNotFoundException(Exception e) {
        return new BasicErrorResponse("account.get.error", "user not found");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicErrorResponse handleDuplicatedAccountException(Exception e) {
        return new BasicErrorResponse("account.create.error", "duplicated account");
    }
}
