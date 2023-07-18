/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 7:00 PM
 */
package com.ceyentra.springboot.visitersmanager.dto;

import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.enums.VisitorCardStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class VisitorCardDTO {

    int cardId;

    String cardNumber;

    String cardType;

    VisitorCardStatus visitorCardStatus;

    EntityDbStatus dbStatus;

    public VisitorCardDTO(int cardId, String cardNumber, String cardType, VisitorCardStatus visitorCardStatus) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.visitorCardStatus = visitorCardStatus;
    }
}
