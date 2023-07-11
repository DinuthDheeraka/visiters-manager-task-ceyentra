/**
 * @author :  Dinuth Dheeraka
 * Created : 7/11/2023 11:00 PM
 */
package com.ceyentra.springboot.visitersmanager.exceptions;

public class VisitNotFoundException extends RuntimeException{

    public VisitNotFoundException() {
    }

    public VisitNotFoundException(String message) {
        super(message);
    }

    public VisitNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VisitNotFoundException(Throwable cause) {
        super(cause);
    }

    public VisitNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
