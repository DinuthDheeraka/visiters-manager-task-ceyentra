/**
 * @author :  Dinuth Dheeraka
 * Created : 7/11/2023 11:00 PM
 */
package com.ceyentra.springboot.visitersmanager.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class VisitException extends RuntimeException{

    private int statusCode;

    private HttpStatus httpStatus;

    public VisitException() {
    }

    public VisitException(String message) {
        super(message);
    }

    public VisitException(String message,int statusCode,HttpStatus httpStatus){
        super(message);
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
    }
}
