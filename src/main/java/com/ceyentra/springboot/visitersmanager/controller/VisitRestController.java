/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 9:49 PM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.request.RequestVisitDTO;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitNotFoundException;
import com.ceyentra.springboot.visitersmanager.service.VisitService;
import com.ceyentra.springboot.visitersmanager.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/visits")
public class VisitRestController {

    private final VisitService visitService;

    @Autowired
    public VisitRestController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping
    public ResponseEntity<ResponseUtil<List<VisitDTO>>> getAllVisits() {

        Optional<List<VisitDTO>> visitDTOList = Optional.ofNullable(visitService.readAllVisits());

        if (visitDTOList.isEmpty()) {
            throw new VisitNotFoundException("couldn't find visits");
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(), "successfully retrieved visits",
                visitDTOList.get()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUtil<VisitDTO>> getVisitById(@PathVariable int id) {

        Optional<VisitDTO> optionalVisitDTO = Optional.ofNullable(visitService.readVisitById(id));

        if (optionalVisitDTO.isEmpty()) {
            throw new VisitNotFoundException("couldn't find visit - " + id);
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(), "successfully retrieved visit -" + id,
                optionalVisitDTO.get()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseUtil<String>> addVisit(@RequestBody RequestVisitDTO requestVisitDTO) {
        visitService.saveVisit(requestVisitDTO);
        return new ResponseEntity<>(
                new ResponseUtil<>(
                        HttpStatus.CREATED.value(),
                        "successfully saved visit details",
                        "success"
                ),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ResponseUtil<String>> updateVisit(@RequestBody RequestVisitDTO requestVisitDTO) {
        visitService.updateVisitById(requestVisitDTO);
        return new ResponseEntity<>(
                new ResponseUtil<>(
                        HttpStatus.OK.value(),
                        "successfully updated visit details",
                        "success"
                ),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseUtil<String>> deleteVisitById(@PathVariable int id){

        Optional<String> optional = Optional.ofNullable(visitService.deleteVisitById(id));

        if(optional.isEmpty()){
            throw new VisitNotFoundException("couldn't find visit - "+id);
        }

        return new ResponseEntity<>(
                new ResponseUtil<>(
                        HttpStatus.OK.value(),
                        "successfully deleted visit "+id+" details",
                        "success"
                ),
                HttpStatus.OK);
    }
}