package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.enums.VisitorCardStatus;

import java.util.List;

public interface VisitorCardService {

    VisitorCardDTO readVisitorCardById(int id);

    VisitorCardDTO saveVisitorCard(VisitorCardDTO visitorCardDTO);

    VisitorCardDTO updateVisitorCard(VisitorCardDTO visitorCardDTO);

    String deleteVisitorCardBYId(int id);

    List<VisitorCardDTO> readVisitorCardByStatus(VisitorCardStatus status);

    VisitorCardStatus findVisitorCardStatusByCardId(int id);

    int updateVisitorCardDbStatusById(EntityDbStatus status,int id);

    List<VisitorCardDTO> findVisitorCardsByDbStatus(EntityDbStatus entityDbStatus);

    void updateVisitorCardStatusById(String status,int id);
}
