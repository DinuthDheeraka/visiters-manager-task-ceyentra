/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 1:08 AM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.FloorException;
import com.ceyentra.springboot.visitersmanager.repository.FloorRepository;
import com.ceyentra.springboot.visitersmanager.dto.FloorDTO;
import com.ceyentra.springboot.visitersmanager.entity.FloorEntity;
import com.ceyentra.springboot.visitersmanager.service.FloorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FloorServiceImpl implements FloorService {

    private final FloorRepository floorDAO;

    private final ModelMapper modelMapper;

    @Override
    public FloorDTO readFloorById(int id) {
        return modelMapper.map(floorDAO.findById(id), FloorDTO.class);
    }

    @Override
    public List<FloorDTO> readAllFloors() {
        return modelMapper.map(floorDAO.findAll(),
                new TypeToken<ArrayList<FloorDTO>>() {
                }.getType());
    }

    @Override
    public FloorDTO updateFloor(FloorDTO floorDTO) {

        Optional<FloorDTO> optional = Optional.ofNullable(readFloorById(floorDTO.getFloorId()));

        if(optional.isEmpty()){
            throw new FloorException(String.format("Unable to Update Floor Details with Associate ID - %d",floorDTO.getFloorId()));
        }

        FloorDTO currentFloorDTO = optional.get();

        currentFloorDTO.setFloorName(floorDTO.getFloorName()==null?
                currentFloorDTO.getFloorName() : floorDTO.getFloorName());

        currentFloorDTO.setFloorNumber(floorDTO.getFloorNumber()==null?
                currentFloorDTO.getFloorNumber() : floorDTO.getFloorNumber());


        FloorEntity save = floorDAO.save(modelMapper.map(currentFloorDTO, FloorEntity.class));

        return modelMapper.map(save, FloorDTO.class);
    }

    @Override
    public String deleteFloorById(int id) {
        Optional<FloorEntity> byId = floorDAO.findById(id);
        if (byId.isPresent()) {
            floorDAO.deleteById(id);
            return "deleted floor - " + id;
        }
        return null;
    }

    @Override
    public FloorDTO saveFloor(FloorDTO floorDTO) {
        floorDTO.setFloorId(0);
        floorDTO.setEntityDbStatus(EntityDbStatus.ACTIVE);
        FloorEntity save = floorDAO.save(modelMapper.map(floorDTO, FloorEntity.class));
        return modelMapper.map(save, FloorDTO.class);
    }

    @Override
    public int updateFloorDbStatusById(EntityDbStatus status, int id) {
        return floorDAO.setFloorDbStatusById(status.name(),id);
    }

    @Override
    public List<FloorDTO> selectFloorsByDbStatus(EntityDbStatus entityDbStatus) {
        return modelMapper.map(floorDAO.selectFloorsByDbStatus(entityDbStatus.name()),
                new TypeToken<ArrayList<FloorDTO>>() {
                }.getType());
    }
}
