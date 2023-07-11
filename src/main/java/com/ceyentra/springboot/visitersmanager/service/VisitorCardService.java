package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.entity.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.enums.entity.visitorcard.VisitorCardStatus;

import java.util.List;

public interface VisitorCardService {

    List<VisitorCardDTO> readAllVisitorCard();

    VisitorCardDTO readVisitorCardById(int id);

    VisitorCardDTO saveVisitorCard(VisitorCardDTO visitorCardDTO);

    VisitorCardDTO updateVisitorCard(VisitorCardDTO visitorCardDTO);

    String deleteVisitorCardBYId(int id);

    List<VisitorCardDTO> readVisitorCardByStatus(VisitorCardStatus status);

    VisitorCardStatus findVisitorCardStatusByCardId(int id);
}
