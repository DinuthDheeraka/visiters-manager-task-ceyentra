/**
 * @author :  Dinuth Dheeraka
 * Created : 7/12/2023 2:13 AM
 */
package com.ceyentra.springboot.visitersmanager.exceptions;

public class VisitorCardInUseException extends RuntimeException{

    public VisitorCardInUseException() {
    }

    public VisitorCardInUseException(String message) {
        super(message);
    }

    public VisitorCardInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public VisitorCardInUseException(Throwable cause) {
        super(cause);
    }

    public VisitorCardInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
