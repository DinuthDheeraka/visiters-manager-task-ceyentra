/**
 * @author :  Dinuth Dheeraka
 * Created : 7/8/2023 1:32 AM
 */
package com.ceyentra.springboot.visitersmanager.rest;

import com.ceyentra.springboot.visitersmanager.dto.entity.SystemUserDTO;
import com.ceyentra.springboot.visitersmanager.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("v1/{id}")
    public SystemUserDTO getSystemUserById(@PathVariable int id) {
        return systemUserService.readSystemUserById(id);
    }

    @PostMapping("/v1")
    public SystemUserDTO addSystemUser(@RequestBody SystemUserDTO systemUserDTO){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(systemUserDTO.getPassword());

        systemUserDTO.setPassword("{bcrypt}"+hashedPassword);
        return systemUserService.saveSystemUser(systemUserDTO);
    }

    @PutMapping("/v1")
    public SystemUserDTO updateSystemUser(@RequestBody SystemUserDTO systemUserDTO){
        return systemUserService.updateSystemUser(systemUserDTO);
    }

    @DeleteMapping("v1/{id}")
    public String updateSystemUser(@PathVariable int id){
        return systemUserService.deleteSystemUserById(id);
    }

}
