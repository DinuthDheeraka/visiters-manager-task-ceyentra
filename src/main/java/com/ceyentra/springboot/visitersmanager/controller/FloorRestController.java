/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 2:51 AM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.dto.FloorDTO;
import com.ceyentra.springboot.visitersmanager.exceptions.FloorNotFoundException;
import com.ceyentra.springboot.visitersmanager.service.FloorService;
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
@RequestMapping("/api/v1/floors")
@PreAuthorize("hasRole('ADMIN') or hasRole('RECEPTIONIST')")
public class FloorRestController {

    private final FloorService floorService;

    @Autowired
    public FloorRestController(FloorService floorService) {
        this.floorService = floorService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<List<FloorDTO>>> getAllVisitorCard() {

        Optional<List<FloorDTO>> optional = Optional.ofNullable(floorService.readAllFloors());

        if (optional.isEmpty()) {
            throw new FloorNotFoundException("unable to find floors");
        }

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                "successfully retrieved floors details",
                optional.get()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<FloorDTO>> getFloorById(@PathVariable int id) {

        Optional<FloorDTO> optional = Optional.ofNullable(floorService.readFloorById(id));

        if (optional.isEmpty()) {
            throw new FloorNotFoundException("unable to find floor - "+id);
        }

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                "successfully retrieved floor details - "+id,
                optional.get()),
                HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ResponseUtil<FloorDTO>> updateFloor(@RequestBody FloorDTO floorDTO) {

        Optional<FloorDTO> optional = Optional.ofNullable(floorService.updateFloor(floorDTO));

        if (optional.isEmpty()) {
            throw new FloorNotFoundException("unable to update floor - "+floorDTO.getFloorId());
        }

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                "successfully updated floor details - "+optional.get(),
                optional.get()),
                HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ResponseUtil<FloorDTO>> addFloor(@RequestBody FloorDTO floorDTO) {

        Optional<FloorDTO> optional = Optional.ofNullable(floorService.saveFloor(floorDTO));

        if (optional.isEmpty()) {
            throw new FloorNotFoundException("unable to save floor details");
        }

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                "successfully saved floor details - "+optional.get(),
                optional.get()),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<ResponseUtil<String>> deleteFloorById(@PathVariable int id) {

        Optional<String> optional = Optional.ofNullable(floorService.deleteFloorById(id));

        if (optional.isEmpty()) {
            throw new FloorNotFoundException("unable to find floor - "+id);
        }

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                "successfully deleted floor",
                optional.get()),
                HttpStatus.OK);
    }
}
