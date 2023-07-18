/**
 * @author :  Dinuth Dheeraka
 * Created : 7/14/2023 3:28 AM
 */
package com.ceyentra.springboot.visitersmanager.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class VisitorCardException extends RuntimeException{

    private int statusCode;

    private HttpStatus httpStatus;

    public VisitorCardException() {
    }

    public VisitorCardException(String message) {
        super(message);
    }

    public VisitorCardException(String message,int statusCode,HttpStatus httpStatus) {
        super(message);
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
    }
}
