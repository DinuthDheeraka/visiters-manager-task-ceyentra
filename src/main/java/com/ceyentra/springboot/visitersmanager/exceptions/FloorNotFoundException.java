/**
 * @author :  Dinuth Dheeraka
 * Created : 7/14/2023 10:02 AM
 */
package com.ceyentra.springboot.visitersmanager.exceptions;

public class FloorNotFoundException extends RuntimeException{

    public FloorNotFoundException() {
    }

    public FloorNotFoundException(String message) {
        super(message);
    }

    public FloorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FloorNotFoundException(Throwable cause) {
        super(cause);
    }

    public FloorNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
