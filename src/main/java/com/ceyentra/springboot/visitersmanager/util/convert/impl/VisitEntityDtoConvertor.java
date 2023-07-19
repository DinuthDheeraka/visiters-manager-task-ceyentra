/**
 * @author :  Dinuth Dheeraka
 * Created : 7/15/2023 8:34 PM
 */
package com.ceyentra.springboot.visitersmanager.util.convert.impl;

import com.ceyentra.springboot.visitersmanager.dto.FloorDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.entity.VisitEntity;
import com.ceyentra.springboot.visitersmanager.util.convert.CustomConvertor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VisitEntityDtoConvertor implements CustomConvertor<VisitEntity, VisitDTO> {

    private final ModelMapper modelMapper;

    @Override
    public List<VisitDTO> convert(List<VisitEntity> visitEntityList) {

        if(visitEntityList==null){
            return new ArrayList<>();
        }

        return visitEntityList
                .stream()
                .map(visit -> {
                    //visitDTO
                    VisitDTO visitDTO = new VisitDTO(
                            visit.getVisitId(), visit.getCheckInDate(),
                            visit.getCheckInTime(), visit.getCheckOutTime(),
                            visit.getReason(), visit.getVisitStatus());

                    //set DB status
                    visitDTO.setDbStatus(visit.getDbStatus());

                    //visitorDTO
                    VisitorDTO visitorDTO = modelMapper.map(visit.getVisitor(), VisitorDTO.class);

                    //visitorCardDTO
                    VisitorCardDTO visitorCardDTO = modelMapper.map(visit.getVisitorCard(), VisitorCardDTO.class);

                    //floorDTO
                    FloorDTO floorDTO = modelMapper.map(visit.getFloor(), FloorDTO.class);

                    visitDTO.setVisitor(visitorDTO);
                    visitDTO.setVisitorCard(visitorCardDTO);
                    visitDTO.setFloor(floorDTO);

                    return visitDTO;
                })
                .collect(Collectors.toList());
    }
}
