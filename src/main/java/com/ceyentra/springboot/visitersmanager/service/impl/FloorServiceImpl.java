/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 1:08 AM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.dto.FloorDTO;
import com.ceyentra.springboot.visitersmanager.entity.FloorEntity;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.FloorException;
import com.ceyentra.springboot.visitersmanager.repository.FloorRepository;
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
        try {
            Optional<FloorEntity> byId = floorDAO.findById(id);

            if (byId.isEmpty() || byId.get().getDbStatus()==EntityDbStatus.DELETED) {
                throw new FloorException(String.format("Unable to find Floor with Associate ID - %d.", id));
            }

            return modelMapper.map(byId.get(), FloorDTO.class);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public FloorDTO updateFloor(FloorDTO floorDTO) {

        try {

            Optional<FloorDTO> optional = Optional.ofNullable(readFloorById(floorDTO.getFloorId()));

            if (optional.isEmpty()||optional.get().getEntityDbStatus()==EntityDbStatus.DELETED) {
                throw new FloorException(String.format("Unable to Update Floor Details with Associate ID - %d", floorDTO.getFloorId()));
            }

            FloorDTO currentFloorDTO = optional.get();

            currentFloorDTO.setFloorName(floorDTO.getFloorName() == null ?
                    currentFloorDTO.getFloorName() : floorDTO.getFloorName());

            currentFloorDTO.setFloorNumber(floorDTO.getFloorNumber() == null ?
                    currentFloorDTO.getFloorNumber() : floorDTO.getFloorNumber());

            FloorEntity save = floorDAO.save(modelMapper.map(currentFloorDTO, FloorEntity.class));

            return modelMapper.map(save, FloorDTO.class);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public FloorDTO saveFloor(FloorDTO floorDTO) {

        try {

            floorDTO.setFloorId(0);
            floorDTO.setEntityDbStatus(EntityDbStatus.ACTIVE);

            return modelMapper.map(floorDAO.save(modelMapper.map(
                    floorDTO,
                    FloorEntity.class)),
                    FloorDTO.class);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void updateFloorDbStatusById(EntityDbStatus status, int id) {

        try{

            Optional<FloorEntity> byId = floorDAO.findById(id);

            if(byId.isEmpty()||byId.get().getDbStatus()==EntityDbStatus.DELETED){
                throw new FloorException(String.format("Unable to Find Floor with Associate ID - %d.",id));
            }

            floorDAO.setFloorDbStatusById(status.name(), id);

        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<FloorDTO> selectFloorsByDbStatus(EntityDbStatus entityDbStatus) {

        try{

            Optional<List<FloorEntity>> floorEntities = Optional.ofNullable(floorDAO.selectFloorsByDbStatus(entityDbStatus));

            if(floorEntities.isEmpty()){
                throw new FloorException("There are no any Registered Floors.");
            }

            return modelMapper.map(floorEntities.get(),
                    new TypeToken<ArrayList<FloorDTO>>() {
                    }.getType());

        }catch (Exception e){
            throw e;
        }
    }
}
