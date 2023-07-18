/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 2:51 AM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.dto.FloorDTO;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.FloorException;
import com.ceyentra.springboot.visitersmanager.service.FloorService;
import com.ceyentra.springboot.visitersmanager.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class FloorRestController {

    private final FloorService floorService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<List<FloorDTO>>> getAllFloors() {

        Optional<List<FloorDTO>> optional = Optional.ofNullable(floorService.selectFloorsByDbStatus(EntityDbStatus.ACTIVE));

        if (optional.isEmpty()) {
            throw new FloorException("There are no any Registered Floors.");
        }

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                "Successfully Retrieved Floors Details.",
                optional.get()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<FloorDTO>> getFloorById(@PathVariable int id) {

        Optional<FloorDTO> optional = Optional.ofNullable(floorService.readFloorById(id));

        if (optional.isEmpty() || optional.get().getEntityDbStatus()==EntityDbStatus.DELETED) {
            throw new FloorException(String.format("Unable to find Floor with Associate ID - %d.",id));
        }

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                String.format("Successfully Retrieved Floor Details for Associate ID - %d.",id),
                optional.get()),
                HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ResponseUtil<FloorDTO>> updateFloor(@RequestBody FloorDTO floorDTO) {

        Optional<FloorDTO> optional = Optional.ofNullable(floorService.updateFloor(floorDTO));

        if (optional.isEmpty()) {
            throw new FloorException(String.format("Unable to Update Floor Details with Associate ID - %d.",floorDTO.getFloorId()));
        }

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                String.format("Successfully Updated Floor Details with Associate ID - %d.",floorDTO.getFloorId()),
                optional.get()),
                HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ResponseUtil<FloorDTO>> addFloor(@RequestBody FloorDTO floorDTO) {

        Optional<FloorDTO> optional = Optional.ofNullable(floorService.saveFloor(floorDTO));

        if (optional.isEmpty()) {
            throw new FloorException("Unable to Save Floor Details.");
        }

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                String.format("Successfully Saved Floor Details with Floor ID - %d.",optional.get().getFloorId()),
                optional.get()),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<ResponseUtil<String>> deleteFloorById(@PathVariable int id) {

        int count = floorService.updateFloorDbStatusById(EntityDbStatus.DELETED,id);

        if (count<=0) {
            throw new FloorException(String.format("Unable to Find Floor with Associate ID - %d.",id));
        }

        return new ResponseEntity<>(new ResponseUtil<>(HttpStatus.OK.value(),
                "Successfully Deleted Floor",
                String.format("Deleted Floor with Associate ID - %d.",id)),
                HttpStatus.OK);
    }
}
