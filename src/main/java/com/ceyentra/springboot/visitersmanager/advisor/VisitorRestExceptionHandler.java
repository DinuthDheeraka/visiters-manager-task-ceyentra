/**
 * @author :  Dinuth Dheeraka
 * Created : 7/11/2023 1:00 PM
 */
package com.ceyentra.springboot.visitersmanager.advisor;

import com.ceyentra.springboot.visitersmanager.exceptions.VisitorNotFoundException;
import com.ceyentra.springboot.visitersmanager.exceptions.response.VisitorErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VisitorRestExceptionHandler {

    public ResponseEntity<VisitorErrorResponse> handleException(VisitorNotFoundException e){

        return new ResponseEntity(
                new VisitorErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        "Visitor not found",
                        System.currentTimeMillis())
                , HttpStatus.NOT_FOUND
        );
    }
}
