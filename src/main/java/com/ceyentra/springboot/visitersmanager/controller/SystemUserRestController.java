/**
 * @author :  Dinuth Dheeraka
 * Created : 7/8/2023 1:32 AM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.dto.SystemUserDTO;
import com.ceyentra.springboot.visitersmanager.exceptions.SystemUserNotFoundException;
import com.ceyentra.springboot.visitersmanager.service.SystemUserService;
import com.ceyentra.springboot.visitersmanager.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
@PreAuthorize("hasRole('ADMIN')")
public class SystemUserRestController {

    private final SystemUserService systemUserService;

    @Autowired
    public SystemUserRestController(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<ResponseUtil<List<SystemUserDTO>>> getAllSystemUsers() {

        Optional<List<SystemUserDTO>> optional =
                Optional.ofNullable(systemUserService.readAllSystemUsers());

        if (optional.isEmpty()) {
            throw new SystemUserNotFoundException("couldn't find system users");
        }

        return new ResponseEntity<>(
                new ResponseUtil<>(
                        HttpStatus.OK.value(),
                        "successfully retrieved system users",
                        optional.get()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<ResponseUtil<SystemUserDTO>> getSystemUserById(@PathVariable int id) {
        Optional<SystemUserDTO> optional = Optional.ofNullable(systemUserService.readSystemUserById(id));

        if (optional.isEmpty()) {
            throw new SystemUserNotFoundException("couldn't find system user -" + id);
        }

        return new ResponseEntity<>(
                new ResponseUtil<>(
                        HttpStatus.OK.value(),
                        "successfully retrieved system user - " + id,
                        optional.get()),
                HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<ResponseUtil<SystemUserDTO>> addSystemUser(@RequestBody SystemUserDTO systemUserDTO) {
//
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        systemUserDTO.setPassword("{bcrypt}" + passwordEncoder.encode(systemUserDTO.getPassword()));
//
//        return new ResponseEntity<>(
//                new ResponseUtil<>(
//                        HttpStatus.OK.value(),
//                        "successfully saved system user",
//                        systemUserService.saveSystemUser(systemUserDTO)),
//                HttpStatus.OK);
//    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ResponseUtil<SystemUserDTO>> updateSystemUser(@RequestBody SystemUserDTO systemUserDTO) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        systemUserDTO.setPassword("{bcrypt}"+passwordEncoder.encode(systemUserDTO.getPassword()));

        return new ResponseEntity<>(
                new ResponseUtil<>(
                        HttpStatus.OK.value(),
                        "successfully saved system user",
                        systemUserService.updateSystemUser(systemUserDTO)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<ResponseUtil<String>> deleteSystemUser(@PathVariable int id) {

        Optional<String> optional = Optional.ofNullable(systemUserService.deleteSystemUserById(id));

        if(optional.isEmpty()){
            throw new SystemUserNotFoundException("couldn't find system user -" + id);
        }

        return new ResponseEntity<>(
                new ResponseUtil<>(
                        HttpStatus.OK.value(),
                        "successfully deleted system user",
                        optional.get()),
                HttpStatus.OK);
    }

}
