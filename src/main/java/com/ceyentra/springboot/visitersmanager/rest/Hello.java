/**
 * @author :  Dinuth Dheeraka
 * Created : 7/8/2023 12:27 AM
 */
package com.ceyentra.springboot.visitersmanager.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Hello {

    @GetMapping
    public String s(){
        return "hello";
    }

    @GetMapping("/d")
    public String d(){
        return "d";
    }
}
