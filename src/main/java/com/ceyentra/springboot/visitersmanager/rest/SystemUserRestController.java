/**
 * @author :  Dinuth Dheeraka
 * Created : 7/8/2023 1:32 AM
 */
package com.ceyentra.springboot.visitersmanager.rest;

import com.ceyentra.springboot.visitersmanager.dto.SystemUserDTO;
import com.ceyentra.springboot.visitersmanager.entity.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system_users")
public class SystemUserRestController {

    @Autowired
    public SystemUserRestController() {
    }

    @GetMapping("/v1")
    public List<SystemUserDTO> getAllSystemUsers(){

    }

    @PostMapping(consumes = "application/json")
    public void addSystemUser(@RequestBody SystemUser systemUser){
    }
}
