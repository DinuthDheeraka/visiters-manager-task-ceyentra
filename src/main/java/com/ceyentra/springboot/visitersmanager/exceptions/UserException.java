/**
 * @author :  Dinuth Dheeraka
 * Created : 7/12/2023 12:28 AM
 */
package com.ceyentra.springboot.visitersmanager.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserException extends RuntimeException {

    private int statusCode;

    private HttpStatus httpStatus;

    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message,int statusCode,HttpStatus httpStatus) {
        super(message);
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
    }
}
