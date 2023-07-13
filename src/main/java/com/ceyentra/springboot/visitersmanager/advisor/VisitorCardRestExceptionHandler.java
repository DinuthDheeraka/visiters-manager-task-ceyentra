/**
 * @author :  Dinuth Dheeraka
 * Created : 7/14/2023 3:29 AM
 */
package com.ceyentra.springboot.visitersmanager.advisor;

import com.ceyentra.springboot.visitersmanager.exceptions.VisitorCardNotFoundException;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitorNotFoundException;
import com.ceyentra.springboot.visitersmanager.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VisitorCardRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(VisitorCardNotFoundException e){

        return new ResponseEntity(
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        e.getMessage(),
                        System.currentTimeMillis())
                , HttpStatus.NOT_FOUND
        );
    }
}
