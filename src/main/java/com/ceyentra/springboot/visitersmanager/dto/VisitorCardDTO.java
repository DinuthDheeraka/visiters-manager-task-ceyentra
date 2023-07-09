/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 7:00 PM
 */
package com.ceyentra.springboot.visitersmanager.dto;

import com.ceyentra.springboot.visitersmanager.enums.entity.visitorcard.VisitorCardStatus;

import java.util.List;

public class VisitorCardDTO {

    int cardId;

    String cardNumber;

    String cardType;

    VisitorCardStatus visitorCardStatus;

    List<VisitDTO> visitList;
}
