package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.FloorDTO;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;

import java.util.List;

public interface FloorService {

    FloorDTO readFloorById(int id);

    List<FloorDTO> readAllFloors();

    FloorDTO updateFloor(FloorDTO floorDTO);

    String deleteFloorById(int id);

    FloorDTO saveFloor(FloorDTO floorDTO);

    int updateFloorDbStatusById(EntityDbStatus status,int id);

    List<FloorDTO> selectFloorsByDbStatus(EntityDbStatus entityDbStatus);
}
