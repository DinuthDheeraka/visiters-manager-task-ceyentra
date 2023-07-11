/**
 * @author :  Dinuth Dheeraka
 * Created : 7/12/2023 12:28 AM
 */
package com.ceyentra.springboot.visitersmanager.exceptions;

public class SystemUserNotFoundException extends RuntimeException{

    public SystemUserNotFoundException() {
    }

    public SystemUserNotFoundException(String message) {
        super(message);
    }

    public SystemUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemUserNotFoundException(Throwable cause) {
        super(cause);
    }

    public SystemUserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
