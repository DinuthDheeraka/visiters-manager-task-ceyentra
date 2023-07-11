/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 6:52 PM
 */
package com.ceyentra.springboot.visitersmanager.rest;

import com.ceyentra.springboot.visitersmanager.dto.entity.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitorNotFoundException;
import com.ceyentra.springboot.visitersmanager.service.VisitorService;
import com.ceyentra.springboot.visitersmanager.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/visitors")
public class VisitorRestController {

    private final VisitorService visitorService;

    @Autowired
    public VisitorRestController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @GetMapping("/v2")
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

    @GetMapping("/v2/{id}")
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

    @PostMapping("/v2")
    public ResponseEntity<ResponseUtil<VisitorDTO>> addVisitor(@RequestBody VisitorDTO visitorDTO) {

        return new ResponseEntity(new ResponseUtil<>(
                HttpStatus.OK.value(), "saved visitor successfully",
                visitorService.saveVisitor(visitorDTO)),
                HttpStatus.CREATED);
    }

    @PutMapping("/v2")
    public ResponseEntity<ResponseUtil<VisitorDTO>> updateVisitor(@RequestBody VisitorDTO visitorDTO) {

        return new ResponseEntity(new ResponseUtil<>(
                HttpStatus.OK.value(), "updated visitor successfully",
                visitorService.updateVisitor(visitorDTO)),
                HttpStatus.OK);
    }

    @DeleteMapping("v1/{id}")
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

    @GetMapping("v2/visits/{id}")
    public ResponseEntity<ResponseUtil<List<VisitDTO>>> getVisitsById(@PathVariable int id) {

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(), "retrieved visits for visitor - " + id,
                visitorService.readAllVisitsByVisitorId(id)),
                HttpStatus.OK);
    }

    @DeleteMapping("v1/test")
    public String def() {
        return "test";
    }
}
