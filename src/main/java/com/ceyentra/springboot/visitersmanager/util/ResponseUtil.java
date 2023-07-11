/**
 * @author :  Dinuth Dheeraka
 * Created : 7/8/2023 1:47 AM
 */
package com.ceyentra.springboot.visitersmanager.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUtil <T>{

    private int status;

    private String message;

    private T data;
}
