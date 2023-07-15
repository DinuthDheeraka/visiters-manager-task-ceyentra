/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 6:52 PM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitorNotFoundException;
import com.ceyentra.springboot.visitersmanager.service.VisitorService;
import com.ceyentra.springboot.visitersmanager.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/visitors")
@PreAuthorize("hasRole('ADMIN') or hasRole('RECEPTIONIST')")
public class VisitorRestController {

    private final VisitorService visitorService;

    @Autowired
    public VisitorRestController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<List<VisitorDTO>>> getAllVisitors() {

        Optional<List<VisitorDTO>> optionalVisitorDTOS = Optional.ofNullable(visitorService.readAllVisitors());

        if (optionalVisitorDTOS.isEmpty()) {
            throw new VisitorNotFoundException("unable to find visitors");
        }

        return new ResponseEntity(new ResponseUtil(
                HttpStatus.OK.value(), "successfully retrieved visitors",
                optionalVisitorDTOS.get()),
                HttpStatus.OK
        );

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<VisitorDTO>> getVisitorById(@PathVariable int id) {

        Optional<VisitorDTO> optionalVisitorDTO = Optional.ofNullable(visitorService.readVisitorById(id));

        if (optionalVisitorDTO.isEmpty()) {
            throw new VisitorNotFoundException("couldn't find visitor - " + id);
        }

        return new ResponseEntity(new ResponseUtil<>(
                HttpStatus.OK.value(), "retrieved visitor successfully",
                optionalVisitorDTO.get()),
                HttpStatus.OK);
    }


    @GetMapping("/nic")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<VisitorDTO>> getVisitorByNic(@RequestParam("nic") String nic) {

        Optional<VisitorDTO> optionalVisitorDTO = Optional.ofNullable(visitorService.readVisitorByNic(nic));

        if (optionalVisitorDTO.isEmpty()) {
            throw new VisitorNotFoundException("couldn't find visitor with nic - " + nic);
        }

        return new ResponseEntity(new ResponseUtil<>(
                HttpStatus.OK.value(), "retrieved visitor successfully",
                optionalVisitorDTO.get()),
                HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('admin:create') or hasAuthority('receptionist:create')")
    public ResponseEntity<ResponseUtil<VisitorDTO>> addVisitor(@RequestBody VisitorDTO visitorDTO) {

        System.out.println(visitorDTO);
        return new ResponseEntity(new ResponseUtil<>(
                HttpStatus.CREATED.value(), "saved visitor successfully",
                visitorService.saveVisitor(visitorDTO)),
                HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update') or hasAuthority('receptionist:update')")
    public ResponseEntity<ResponseUtil<VisitorDTO>> updateVisitor(@RequestBody VisitorDTO visitorDTO) {

        return new ResponseEntity(new ResponseUtil<>(
                HttpStatus.OK.value(), "updated visitor successfully",
                visitorService.updateVisitor(visitorDTO)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete') or hasAuthority('receptionist:delete')")
    public ResponseEntity<ResponseUtil<String>> deleteVisitor(@PathVariable int id) {

        Optional<String> response = Optional.ofNullable(visitorService.deleteVisitorById(id));

        if (response.isEmpty()) {
            throw new VisitorNotFoundException("couldn't find visitor - " + id);
        }

        return new ResponseEntity(new ResponseUtil<>(
                HttpStatus.OK.value(), "deleted visitor successfully",
                response.get()),
                HttpStatus.OK);
    }

    @GetMapping("/visits/{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<List<VisitDTO>>> getVisitsById(@PathVariable int id) {

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(), "retrieved visits for visitor - " + id,
                visitorService.readAllVisitsByVisitorId(id)),
                HttpStatus.OK);
    }


}
