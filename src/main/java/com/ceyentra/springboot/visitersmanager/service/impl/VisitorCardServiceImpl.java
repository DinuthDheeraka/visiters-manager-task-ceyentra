/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 12:56 AM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitorCardNotFoundException;
import com.ceyentra.springboot.visitersmanager.repository.VisitorCardRepository;
import com.ceyentra.springboot.visitersmanager.dto.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.entity.VisitorCardEntity;
import com.ceyentra.springboot.visitersmanager.enums.VisitorCardStatus;
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

    private VisitorCardRepository visitorCardDAO;

    private ModelMapper modelMapper;

    @Autowired
    public VisitorCardServiceImpl(VisitorCardRepository visitorCardDAO, ModelMapper modelMapper) {
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
        VisitorCardEntity visitorCard = visitorCardDAO.findById(id).get();
        return modelMapper.map(visitorCard,VisitorCardDTO.class);
    }

    @Override
    public VisitorCardDTO saveVisitorCard(VisitorCardDTO visitorCardDTO) {
        visitorCardDTO.setCardId(0);
        VisitorCardEntity save = visitorCardDAO.save(modelMapper.map(visitorCardDTO, VisitorCardEntity.class));
        return modelMapper.map(save,VisitorCardDTO.class);
    }

    @Override
    public VisitorCardDTO updateVisitorCard(VisitorCardDTO visitorCardDTO) {

        Optional<VisitorCardDTO> optional = Optional.ofNullable(readVisitorCardById(visitorCardDTO.getCardId()));

        if(optional.isEmpty()){
            throw new VisitorCardNotFoundException("couldn't find visitor card - "+visitorCardDTO.getCardId());
        }

        VisitorCardDTO currentVisitorCardDTO = optional.get();

        currentVisitorCardDTO.setVisitorCardStatus(visitorCardDTO.getVisitorCardStatus()==null? currentVisitorCardDTO.getVisitorCardStatus() : visitorCardDTO.getVisitorCardStatus());

        currentVisitorCardDTO.setCardNumber(visitorCardDTO.getCardNumber()==null? currentVisitorCardDTO.getCardNumber():visitorCardDTO.getCardNumber());

        currentVisitorCardDTO.setCardType(visitorCardDTO.getCardType()==null? currentVisitorCardDTO.getCardType():visitorCardDTO.getCardType());

        VisitorCardEntity save = visitorCardDAO.save(modelMapper.map(currentVisitorCardDTO, VisitorCardEntity.class));

        return modelMapper.map(save,VisitorCardDTO.class);
    }

    @Override
    public String deleteVisitorCardBYId(int id) {
        Optional<VisitorCardEntity> byId = visitorCardDAO.findById(id);
        if(byId.isPresent()){
            visitorCardDAO.deleteById(id);
            return "deleted visitor card - "+id;
        }
        return null;
    }

    @Override
    public List<VisitorCardDTO> readVisitorCardByStatus(VisitorCardStatus status) {
        return modelMapper.map(visitorCardDAO.findByVisitorCardStatus(status),
                new TypeToken<ArrayList<VisitorCardDTO>>() {
                }.getType());
    }

    @Override
    public VisitorCardStatus findVisitorCardStatusByCardId(int id) {
        return visitorCardDAO.findVisitorCardStatusByCardId(id);
    }

    @Override
    public int updateVisitorCardDbStatusById(EntityDbStatus status, int id) {
        return visitorCardDAO.updateVisitCardDbStatusById(status.name(),id);
    }

    @Override
    public List<VisitorCardDTO> findVisitorCardsByDbStatus(EntityDbStatus entityDbStatus) {

        return modelMapper.map(visitorCardDAO.findVisitorCardsByDbStatus(entityDbStatus.name()),
                new TypeToken<ArrayList<VisitorCardDTO>>() {
                }.getType());
    }

    @Override
    public void updateVisitorCardStatusById(String status, int id) {
        visitorCardDAO.updateVisitorCardStatusById(status,id);
    }
}
