/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 12:56 AM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.dto.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.entity.VisitorCardEntity;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.enums.VisitorCardStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitorCardException;
import com.ceyentra.springboot.visitersmanager.repository.VisitorCardRepository;
import com.ceyentra.springboot.visitersmanager.service.VisitorCardService;
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
public class VisitorCardServiceImpl implements VisitorCardService {

    private final VisitorCardRepository visitorCardDAO;

    private final ModelMapper modelMapper;

    @Override
    public VisitorCardDTO readVisitorCardById(int id) {

        try {

            Optional<VisitorCardEntity> byId = visitorCardDAO.findById(id);

            if (byId.isEmpty() || byId.get().getDbStatus() == EntityDbStatus.DELETED) {
                throw new VisitorCardException(String.format("Couldn't find Visitor Card with Associate ID - %d", id));
            }

            return modelMapper.map(byId.get(), VisitorCardDTO.class);

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public VisitorCardDTO saveVisitorCard(VisitorCardDTO visitorCardDTO) {

        try {

            visitorCardDTO.setCardId(0);
            visitorCardDTO.setDbStatus(EntityDbStatus.ACTIVE);
            visitorCardDTO.setVisitorCardStatus(visitorCardDTO.getVisitorCardStatus() == null ?
                    VisitorCardStatus.NOT_IN_USE : visitorCardDTO.getVisitorCardStatus());

            return modelMapper.map(visitorCardDAO.save(modelMapper.map(
                    visitorCardDTO,
                    VisitorCardEntity.class)),
                    VisitorCardDTO.class);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public VisitorCardDTO updateVisitorCard(VisitorCardDTO visitorCardDTO) {

        try {

            Optional<VisitorCardDTO> optional = Optional.ofNullable(readVisitorCardById(visitorCardDTO.getCardId()));

            if (optional.isEmpty() || optional.get().getDbStatus() == EntityDbStatus.DELETED) {
                throw new VisitorCardException(String.format("Couldn't find Visitor Card with Associate ID - %d.", visitorCardDTO.getCardId()));
            }

            VisitorCardDTO currentVisitorCardDTO = optional.get();

            currentVisitorCardDTO.setVisitorCardStatus(visitorCardDTO.getVisitorCardStatus() == null ?
                    currentVisitorCardDTO.getVisitorCardStatus() : visitorCardDTO.getVisitorCardStatus());

            currentVisitorCardDTO.setCardNumber(visitorCardDTO.getCardNumber() == null ?
                    currentVisitorCardDTO.getCardNumber() : visitorCardDTO.getCardNumber());

            currentVisitorCardDTO.setCardType(visitorCardDTO.getCardType() == null ?
                    currentVisitorCardDTO.getCardType() : visitorCardDTO.getCardType());

            VisitorCardEntity save = visitorCardDAO.save(modelMapper.map(currentVisitorCardDTO, VisitorCardEntity.class));

            return modelMapper.map(save, VisitorCardDTO.class);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String deleteVisitorCardBYId(int id) {

        try {

            Optional<VisitorCardEntity> byId = visitorCardDAO.findById(id);

            if (byId.isEmpty() || byId.get().getDbStatus() == EntityDbStatus.DELETED) {
                throw new VisitorCardException(String.format("Couldn't find Visitor Card with Associate ID - %d", id));
            }

            visitorCardDAO.deleteById(id);
            return "deleted visitor card - " + id;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<VisitorCardDTO> readVisitorCardByStatus(VisitorCardStatus status) {

        try {

            return modelMapper.map(visitorCardDAO.findByVisitorCardStatusAndDbStatus(status,EntityDbStatus.ACTIVE),
                    new TypeToken<ArrayList<VisitorCardDTO>>() {
                    }.getType());

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public VisitorCardStatus findVisitorCardStatusByCardId(int id) {

        try {

            Optional<VisitorCardEntity> byId = visitorCardDAO.findById(id);

            if (byId.isEmpty() || byId.get().getDbStatus() == EntityDbStatus.DELETED) {
                throw new VisitorCardException(String.format("Couldn't find Visitor Card with Associate ID - %d", id));
            }

            return visitorCardDAO.findVisitorCardStatusByCardId(id);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public int updateVisitorCardDbStatusById(EntityDbStatus status, int id) {

        try {

            Optional<VisitorCardEntity> byId = visitorCardDAO.findById(id);

            if (byId.isEmpty() || byId.get().getDbStatus() == EntityDbStatus.DELETED) {
                throw new VisitorCardException(String.format("Unable to Process.Couldn't find Visitor Card with Associate ID - %d", id));
            }

            return visitorCardDAO.updateVisitCardDbStatusById(status.name(), id);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<VisitorCardDTO> findVisitorCardsByDbStatus(EntityDbStatus entityDbStatus) {

        try {
            return modelMapper.map(visitorCardDAO.findVisitorCardsByDbStatus(entityDbStatus.name()),
                    new TypeToken<ArrayList<VisitorCardDTO>>() {
                    }.getType());

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void updateVisitorCardStatusById(String status, int id) {

        try {

            Optional<VisitorCardEntity> byId = visitorCardDAO.findById(id);

            if (byId.isEmpty() || byId.get().getDbStatus() == EntityDbStatus.DELETED) {
                throw new VisitorCardException(String.format("Unable to Update.Couldn't find Visitor Card with Associate ID - %d", id));
            }

            visitorCardDAO.updateVisitorCardStatusById(status, id);


        } catch (Exception e) {
            throw e;
        }
    }
}
