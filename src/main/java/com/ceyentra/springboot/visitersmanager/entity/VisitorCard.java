/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 6:37 PM
 */
package com.ceyentra.springboot.visitersmanager.entity;

import com.ceyentra.springboot.visitersmanager.enums.entity.visitorcard.VisitorCardStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "visitor_card")
public class VisitorCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    int cardId;

    @Column(name = "card_number")
    String cardNumber;

    @Column(name = "card_type")
    String cardType;

    @Column(name = "card_status")
    VisitorCardStatus visitorCardStatus;

    public VisitorCard(String cardNumber, String cardType,
                       VisitorCardStatus visitorCardStatus) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.visitorCardStatus = visitorCardStatus;
    }

    @Override
    public String toString() {
        return "VisitorCard{" +
                "cardId=" + cardId +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardType='" + cardType + '\'' +
                ", visitorCardStatus=" + visitorCardStatus +
                '}';
    }
}
