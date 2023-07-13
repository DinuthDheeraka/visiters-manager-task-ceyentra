/**
 * @author :  Dinuth Dheeraka
 * Created : 7/14/2023 3:28 AM
 */
package com.ceyentra.springboot.visitersmanager.exceptions;

public class VisitorCardNotFoundException extends RuntimeException{

    public VisitorCardNotFoundException() {
    }

    public VisitorCardNotFoundException(String message) {
        super(message);
    }

    public VisitorCardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VisitorCardNotFoundException(Throwable cause) {
        super(cause);
    }

    public VisitorCardNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
