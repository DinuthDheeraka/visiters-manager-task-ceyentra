/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 2:51 AM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.dto.FloorDTO;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.service.FloorService;
import com.ceyentra.springboot.visitersmanager.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/floors")
@PreAuthorize("hasRole('ADMIN') or hasRole('RECEPTIONIST')")
@RequiredArgsConstructor
public class FloorRestController {

    private final FloorService floorService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<List<FloorDTO>>> getAllFloors() {

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                "Successfully Retrieved Floors Details.",
                floorService.selectFloorsByDbStatus(EntityDbStatus.ACTIVE)),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<FloorDTO>> getFloorById(@PathVariable int id) {

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                String.format("Successfully Retrieved Floor Details for Associate ID - %d.", id),
                floorService.readFloorById(id)),
                HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ResponseUtil<FloorDTO>> updateFloor(@RequestBody FloorDTO floorDTO) {

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                String.format("Successfully Updated Floor Details with Associate ID - %d.", floorDTO.getFloorId()),
                floorService.updateFloor(floorDTO)),
                HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ResponseUtil<FloorDTO>> addFloor(@RequestBody FloorDTO floorDTO) {

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                String.format("Successfully Saved Floor Details with Floor ID - %d.", floorDTO.getFloorId()),
                floorService.saveFloor(floorDTO)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<ResponseUtil<String>> deleteFloorById(@PathVariable int id) {

        floorService.updateFloorDbStatusById(EntityDbStatus.DELETED, id);

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                "Successfully Deleted Floor",
                String.format("Deleted Floor with Associate ID - %d.", id)),
                HttpStatus.OK);
    }
}
