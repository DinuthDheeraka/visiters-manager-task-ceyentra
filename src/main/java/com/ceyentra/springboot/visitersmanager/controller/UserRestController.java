/**
 * @author :  Dinuth Dheeraka
 * Created : 7/13/2023 10:20 PM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.config.auth.AuthenticationService;
import com.ceyentra.springboot.visitersmanager.dto.UserDTO;
import com.ceyentra.springboot.visitersmanager.dto.response.AuthenticationResponseDTO;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.UserException;
import com.ceyentra.springboot.visitersmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/_users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    private final AuthenticationService service;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody UserDTO request) {
        return ResponseEntity.ok(service.register(request));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public List<UserDTO> getAllUsers() {
        return userService.readAllUsersByDbStatus(EntityDbStatus.ACTIVE);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public UserDTO getUserById(@PathVariable int id) {
        Optional<UserDTO> optional = Optional.ofNullable(userService.readUserById(id));
        if (optional.isEmpty()) {
            throw new UserException("user with id -" + id + " not found");
        }
        return optional.get();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin:delete')")
    public void deleteUserById(@PathVariable int id) {
        userService.deleteUserById(id);
    }
}
