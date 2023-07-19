package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.FloorDTO;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;

import java.util.List;

public interface FloorService {

    FloorDTO readFloorById(int id);

    FloorDTO updateFloor(FloorDTO floorDTO);

    FloorDTO saveFloor(FloorDTO floorDTO);

    void updateFloorDbStatusById(EntityDbStatus status,int id);

    List<FloorDTO> selectFloorsByDbStatus(EntityDbStatus entityDbStatus);
}
