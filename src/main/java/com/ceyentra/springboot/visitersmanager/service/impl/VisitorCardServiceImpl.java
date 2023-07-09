/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 12:56 AM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.dao.VisitorCardDAO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.entity.VisitorCard;
import com.ceyentra.springboot.visitersmanager.service.VisitorCardService;
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
public class VisitorCardServiceImpl implements VisitorCardService {

    private VisitorCardDAO visitorCardDAO;

    private ModelMapper modelMapper;

    @Autowired
    public VisitorCardServiceImpl(VisitorCardDAO visitorCardDAO, ModelMapper modelMapper) {
        this.visitorCardDAO = visitorCardDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<VisitorCardDTO> readAllVisitorCard() {
        return modelMapper.map(visitorCardDAO.findAll(),
                new TypeToken<ArrayList<VisitorCardDTO>>() {
                }.getType());
    }

    @Override
    public VisitorCardDTO readVisitorCardById(int id) {
        VisitorCard visitorCard = visitorCardDAO.findById(id).get();
        return modelMapper.map(visitorCard,VisitorCardDTO.class);
    }

    @Override
    public VisitorCardDTO saveVisitorCard(VisitorCardDTO visitorCardDTO) {
        visitorCardDTO.setCardId(0);
        VisitorCard save = visitorCardDAO.save(modelMapper.map(visitorCardDTO, VisitorCard.class));
        return modelMapper.map(save,VisitorCardDTO.class);
    }

    @Override
    public VisitorCardDTO updateVisitorCard(VisitorCardDTO visitorCardDTO) {
        VisitorCard save = visitorCardDAO.save(modelMapper.map(visitorCardDTO, VisitorCard.class));
        return modelMapper.map(save,VisitorCardDTO.class);
    }

    @Override
    public String deleteVisitorCardBYId(int id) {
        Optional<VisitorCard> byId = visitorCardDAO.findById(id);
        if(byId.isPresent()){
            visitorCardDAO.deleteById(id);
            return "deletes visitor card - "+id;
        }
        return "unable to find visitor card - "+id;
    }
}
