/**
 * @author :  Dinuth Dheeraka
 * Created : 7/11/2023 12:46 PM
 */
package com.ceyentra.springboot.visitersmanager.exceptions;

public class VisitorNotFoundException extends RuntimeException{

    public VisitorNotFoundException() {
    }

    public VisitorNotFoundException(String message) {
        super(message);
    }

    public VisitorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VisitorNotFoundException(Throwable cause) {
        super(cause);
    }

    public VisitorNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
