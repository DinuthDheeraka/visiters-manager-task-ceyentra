/**
 * @author :  Dinuth Dheeraka
 * Created : 7/12/2023 12:50 AM
 */
package com.ceyentra.springboot.visitersmanager.advisor;

import com.ceyentra.springboot.visitersmanager.exceptions.SystemUserNotFoundException;
import com.ceyentra.springboot.visitersmanager.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SystemUserRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(SystemUserNotFoundException e) {

        return new ResponseEntity(
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        e.getMessage(),
                        System.currentTimeMillis())
                , HttpStatus.NOT_FOUND
        );
    }
}
