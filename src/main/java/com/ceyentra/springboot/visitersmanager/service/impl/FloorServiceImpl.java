/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 1:08 AM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.repository.FloorRepository;
import com.ceyentra.springboot.visitersmanager.dto.FloorDTO;
import com.ceyentra.springboot.visitersmanager.entity.FloorEntity;
import com.ceyentra.springboot.visitersmanager.service.FloorService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FloorServiceImpl implements FloorService {

    private final FloorRepository floorDAO;

    private final ModelMapper modelMapper;

    @Autowired
    public FloorServiceImpl(FloorRepository floorDAO, ModelMapper modelMapper) {
        this.floorDAO = floorDAO;
        this.modelMapper = modelMapper;
    }


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
        FloorEntity save = floorDAO.save(modelMapper.map(floorDTO, FloorEntity.class));
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
        FloorEntity save = floorDAO.save(modelMapper.map(floorDTO, FloorEntity.class));
        return modelMapper.map(save, FloorDTO.class);
    }
}
