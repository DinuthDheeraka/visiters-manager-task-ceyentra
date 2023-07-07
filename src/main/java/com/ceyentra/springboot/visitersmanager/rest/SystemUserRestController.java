/**
 * @author :  Dinuth Dheeraka
 * Created : 7/8/2023 1:32 AM
 */
package com.ceyentra.springboot.visitersmanager.rest;

import com.ceyentra.springboot.visitersmanager.entity.SystemUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system_users")
public class SystemUserRestController {

    @PostMapping(consumes = "application/json")
    public void addSystemUser(@RequestBody SystemUser systemUser){

    }
}
