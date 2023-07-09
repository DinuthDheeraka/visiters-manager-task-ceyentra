/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 1:08 AM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.dao.FloorDAO;
import com.ceyentra.springboot.visitersmanager.dto.entity.FloorDTO;
import com.ceyentra.springboot.visitersmanager.entity.Floor;
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

    private final FloorDAO floorDAO;

    private final ModelMapper modelMapper;

    @Autowired
    public FloorServiceImpl(FloorDAO floorDAO, ModelMapper modelMapper) {
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
        Floor save = floorDAO.save(modelMapper.map(floorDTO, Floor.class));
        return modelMapper.map(save, FloorDTO.class);
    }

    @Override
    public String deleteFloorById(int id) {
        Optional<Floor> byId = floorDAO.findById(id);
        if (byId.isPresent()) {
            floorDAO.deleteById(id);
            return "deletes floor - " + id;
        }
        return "unable to find floor - " + id;
    }

    @Override
    public FloorDTO saveFloor(FloorDTO floorDTO) {
        floorDTO.setFloorId(0);
        Floor save = floorDAO.save(modelMapper.map(floorDTO, Floor.class));
        return modelMapper.map(save, FloorDTO.class);
    }
}
