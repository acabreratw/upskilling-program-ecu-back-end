package com.thoughtworks.lpe.be_template.exceptions;

import com.thoughtworks.lpe.be_template.controllers.resources.ErrorResponse;
import com.thoughtworks.lpe.be_template.exceptions.enums.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLSyntaxErrorException;

import static com.thoughtworks.lpe.be_template.exceptions.enums.Error.ENTITY_NOT_FOUND;
import static com.thoughtworks.lpe.be_template.exceptions.enums.Error.SQL_SYNTAX_ERROR_EXCEPTION;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionsAdvice {

    @ExceptionHandler({LogicBusinessException.class})
    public ResponseEntity<ErrorResponse> handleBusinessException(
            LogicBusinessException e) {
        return error(BAD_REQUEST, e.getError());
    }

    @ExceptionHandler({SQLSyntaxErrorException.class})
    public ResponseEntity<ErrorResponse> handleBusinessException(
            SQLSyntaxErrorException e) {
        return error(INTERNAL_SERVER_ERROR, SQL_SYNTAX_ERROR_EXCEPTION, e);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotEntityFound(EntityNotFoundException e){
        return error(NOT_FOUND, ENTITY_NOT_FOUND,e);
    }

    private ResponseEntity<ErrorResponse> error(HttpStatus status,
                                                Error error, Exception... e) {
        if(e.length == 0) {
            return ResponseEntity.status(status).body(new ErrorResponse(
                    error.getCode(),
                    error.getDeveloperMessage(),
                    error.getUserMessage()));
        }
        return ResponseEntity.status(status).body(new ErrorResponse(
                error.getCode(),
                e[0].getMessage(),
                error.getUserMessage()));
    }
}