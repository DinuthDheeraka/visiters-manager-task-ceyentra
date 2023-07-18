/**
 * @author :  Dinuth Dheeraka
 * Created : 7/18/2023 12:19 PM
 */
package com.ceyentra.springboot.visitersmanager.exceptions;

import com.ceyentra.springboot.visitersmanager.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleAnyException(Exception e) {

        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.CONTINUE.value(),
                e.getMessage(),
                System.currentTimeMillis()),
                HttpStatus.OK);
    }

    @ExceptionHandler(value = FloorException.class)
    public ResponseEntity<ErrorResponse> handleFloorException(FloorException e) {

        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.CONTINUE.value(),
                e.getMessage(),
                System.currentTimeMillis()),
                HttpStatus.OK);
    }

    @ExceptionHandler(value = VisitorException.class)
    public ResponseEntity<ErrorResponse> handleVisitorException(VisitorException e) {

        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.CONTINUE.value(),
                e.getMessage(),
                System.currentTimeMillis()),
                HttpStatus.OK);
    }

    @ExceptionHandler(value = VisitException.class)
    public ResponseEntity<ErrorResponse> handleVisitException(VisitException e) {

        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.CONTINUE.value(),
                e.getMessage(),
                System.currentTimeMillis()),
                HttpStatus.OK);
    }

    @ExceptionHandler(value = VisitorCardException.class)
    public ResponseEntity<ErrorResponse> handleVisitorCardException(VisitorCardException e) {

        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.CONTINUE.value(),
                e.getMessage(),
                System.currentTimeMillis()),
                HttpStatus.OK);
    }

    @ExceptionHandler(value = UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException e) {

        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.CONTINUE.value(),
                e.getMessage(),
                System.currentTimeMillis()),
                HttpStatus.OK);
    }
}
