/**
 * @author :  Dinuth Dheeraka
 * Created : 7/13/2023 10:20 PM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.dto.UserDTO;
import com.ceyentra.springboot.visitersmanager.exceptions.UserException;
import com.ceyentra.springboot.visitersmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/_users")
@PreAuthorize("hasRole('ADMIN')")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public List<UserDTO> getAllUsers(){
        return userService.readAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public UserDTO getUserById(@PathVariable int id){
        Optional<UserDTO> optional = Optional.ofNullable(userService.readUserById(id));
        if(optional.isEmpty()){
            throw new UserException("user with id -"+id+" not found");
        }
        return optional.get();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public UserDTO updateUser(@RequestBody UserDTO userDTO){
        return userService.updateUser(userDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin:delete')")
    public String deleteUserById(@PathVariable int id){
        return userService.deleteUserById(id);
    }
}
