/**
 * @author :  Dinuth Dheeraka
 * Created : 7/8/2023 1:32 AM
 */
package com.ceyentra.springboot.visitersmanager.rest;

import com.ceyentra.springboot.visitersmanager.dto.SystemUserDTO;
import com.ceyentra.springboot.visitersmanager.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class SystemUserRestController {

    private final SystemUserService systemUserService;

    @Autowired
    public SystemUserRestController(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @GetMapping("/v2")
    public String def(){
        return "def";
    }

    @GetMapping("/v1")
    public List<SystemUserDTO> getAllSystemUsers() {
        return systemUserService.readAllSystemUsers();
    }
}
