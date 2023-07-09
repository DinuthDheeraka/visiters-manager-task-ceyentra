package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.entity.FloorDTO;

import java.util.List;

public interface FloorService {

    FloorDTO readFloorById(int id);

    List<FloorDTO> readAllFloors();

    FloorDTO updateFloor(FloorDTO floorDTO);

    String deleteFloorById(int id);

    FloorDTO saveFloor(FloorDTO floorDTO);
}
