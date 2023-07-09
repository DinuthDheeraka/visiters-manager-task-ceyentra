/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 7:00 PM
 */
package com.ceyentra.springboot.visitersmanager.dto;

import com.ceyentra.springboot.visitersmanager.enums.entity.visitorcard.VisitorCardStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class VisitorCardDTO {

    int cardId;

    String cardNumber;

    String cardType;

    VisitorCardStatus visitorCardStatus;

    List<VisitDTO> visitList;

    public VisitorCardDTO(int cardId, String cardNumber, String cardType, VisitorCardStatus visitorCardStatus) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.visitorCardStatus = visitorCardStatus;
    }
}
