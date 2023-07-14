/**
 * @author :  Dinuth Dheeraka
 * Created : 7/14/2023 10:01 AM
 */
package com.ceyentra.springboot.visitersmanager.advisor;

import com.ceyentra.springboot.visitersmanager.exceptions.FloorNotFoundException;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitorNotFoundException;
import com.ceyentra.springboot.visitersmanager.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FloorRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(FloorNotFoundException e){

        return new ResponseEntity(
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        e.getMessage(),
                        System.currentTimeMillis())
                , HttpStatus.NOT_FOUND
        );
    }
}
