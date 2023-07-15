/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 7:56 PM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.repository.VisitorRepository;
import com.ceyentra.springboot.visitersmanager.dto.FloorDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.entity.VisitEntity;
import com.ceyentra.springboot.visitersmanager.entity.VisitorEntity;
import com.ceyentra.springboot.visitersmanager.service.VisitorService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorDAO;

    private final ModelMapper modelMapper;

    @Override
    public List<VisitorDTO> readAllVisitors() {
        return modelMapper.map(visitorDAO.findAll(),
                new TypeToken<ArrayList<VisitorDTO>>() {
                }.getType());
    }

    @Override
    public VisitorDTO saveVisitor(VisitorDTO visitorDTO) {
        System.out.println(visitorDTO);
        visitorDTO.setVisitorId(0);
        VisitorEntity save = visitorDAO.save(modelMapper.map(visitorDTO, VisitorEntity.class));
        return modelMapper.map(save, VisitorDTO.class);
    }

    @Override
    public VisitorDTO updateVisitor(VisitorDTO visitorDTO) {
        VisitorEntity save = visitorDAO.save(modelMapper.map(visitorDTO, VisitorEntity.class));
        return modelMapper.map(save, VisitorDTO.class);
    }

    @Override
    public String deleteVisitorById(int id) {
        Optional<VisitorEntity> visitor = visitorDAO.findById(id);
        if (visitor.isPresent()) {
            visitorDAO.deleteById(id);
            return "deleted visitor - " + id;
        }
        return null;
    }

    @Override
    public VisitorDTO readVisitorById(int id) {
        Optional<VisitorEntity> byId = visitorDAO.findById(id);
        if(byId.isEmpty()){
            return null;
        }
        return modelMapper.map(byId.get(), VisitorDTO.class);
    }

    @Override
    public VisitorDTO readVisitorByNic(String nic) {
        return modelMapper.map(visitorDAO.findByNic(nic),VisitorDTO.class);
    }

    @Override
    public List<VisitDTO> readAllVisitsByVisitorId(int id) {

        List<VisitDTO> visitDTOS = new ArrayList();
        for (VisitEntity visit : visitorDAO.findVisitsByVisitorId(id).getVisitList()) {

            //visitDTO
            VisitDTO visitDTO = new VisitDTO(visit.getVisitId(), visit.getCheckInDate(),
                    visit.getCheckInTime(), visit.getCheckOutTime(),
                    visit.getReason(), visit.getVisitStatus());

            //visitorDTO
            VisitorDTO visitorDTO = modelMapper.map(visit.getVisitor(), VisitorDTO.class);

            //visitorCardDTO
            VisitorCardDTO visitorCardDTO = modelMapper.map(visit.getVisitorCard(), VisitorCardDTO.class);

            //floorDTO
            FloorDTO floorDTO = modelMapper.map(visit.getFloor(), FloorDTO.class);

            visitDTO.setVisitor(visitorDTO);
            visitDTO.setVisitorCard(visitorCardDTO);
            visitDTO.setFloor(floorDTO);

            visitDTOS.add(visitDTO);
        }
        return visitDTOS;
    }
}
