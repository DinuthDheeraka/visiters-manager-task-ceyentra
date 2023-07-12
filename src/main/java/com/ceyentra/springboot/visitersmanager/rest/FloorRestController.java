/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 2:51 AM
 */
package com.ceyentra.springboot.visitersmanager.rest;

import com.ceyentra.springboot.visitersmanager.dto.entity.FloorDTO;
import com.ceyentra.springboot.visitersmanager.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/floors")
public class FloorRestController {

    private final FloorService floorService;

    @Autowired
    public FloorRestController(FloorService floorService) {
        this.floorService = floorService;
    }

    @GetMapping("/v2")
    public List<FloorDTO> getAllVisitorCard() {
        return floorService.readAllFloors();
    }

    @GetMapping("/v2/{id}")
    public FloorDTO getFloorById(@PathVariable int id) {
        return floorService.readFloorById(id);
    }

    @PutMapping("/v1")
    public FloorDTO updateFloor(@RequestBody FloorDTO floorDTO) {
        return floorService.updateFloor(floorDTO);
    }

    @PostMapping("/v1")
    public FloorDTO addFloor(@RequestBody FloorDTO floorDTO) {
        return floorService.saveFloor(floorDTO);
    }

    @DeleteMapping("/v1/{id}")
    public String deleteFloorById(@PathVariable int id) {
        return floorService.deleteFloorById(id);
    }
}
