/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 6:37 PM
 */
package com.ceyentra.springboot.visitersmanager.entity;

import jakarta.persistence.*;

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
    String cardStatus;
}
