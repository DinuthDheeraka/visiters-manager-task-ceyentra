/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 2:51 AM
 */
package com.ceyentra.springboot.visitersmanager.rest;

import com.ceyentra.springboot.visitersmanager.dto.entity.FloorDTO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/floor")
public class FloorRestController {

    private final FloorService floorService;

    @Autowired
    public FloorRestController(FloorService floorService) {
        this.floorService = floorService;
    }

    @GetMapping
    public List<FloorDTO> getAllVisitorCard() {
        return floorService.readAllFloors();
    }

    @GetMapping("/{id}")
    public FloorDTO getFloorById(@PathVariable int id) {
        return floorService.readFloorById(id);
    }

    @PutMapping
    public FloorDTO updateFloor(@RequestBody FloorDTO floorDTO) {
        return floorService.updateFloor(floorDTO);
    }

    @PostMapping
    public FloorDTO addFloor(@RequestBody FloorDTO floorDTO) {
        return floorService.saveFloor(floorDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteFloorById(@PathVariable int id) {
        return floorService.deleteFloorById(id);
    }
}
