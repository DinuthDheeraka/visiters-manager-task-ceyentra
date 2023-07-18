/**
 * @author :  Dinuth Dheeraka
 * Created : 7/11/2023 12:46 PM
 */
package com.ceyentra.springboot.visitersmanager.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class VisitorException extends RuntimeException {

    private int statusCode;

    private HttpStatus httpStatus;

    public VisitorException() {
    }

    public VisitorException(String message) {
        super(message);
    }

    public VisitorException(String message, int statusCode, HttpStatus httpStatus) {
        super(message);
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
    }
}
