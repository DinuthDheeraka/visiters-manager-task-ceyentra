/**
 * @author :  Dinuth Dheeraka
 * Created : 7/14/2023 10:02 AM
 */
package com.ceyentra.springboot.visitersmanager.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
public class FloorException extends RuntimeException {

    private int statusCode;

    private HttpStatus httpStatus;

    public FloorException() {
    }

    public FloorException(String message) {
        super(message);
    }

    public FloorException(String message,int statusCode,HttpStatus httpStatus){
        super(message);
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
    }
}
