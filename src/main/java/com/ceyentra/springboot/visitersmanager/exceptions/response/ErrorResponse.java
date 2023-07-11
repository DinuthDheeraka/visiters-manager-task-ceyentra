/**
 * @author :  Dinuth Dheeraka
 * Created : 7/11/2023 12:55 PM
 */
package com.ceyentra.springboot.visitersmanager.exceptions.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int status;

    private String message;

    private long timeStamp;
}
