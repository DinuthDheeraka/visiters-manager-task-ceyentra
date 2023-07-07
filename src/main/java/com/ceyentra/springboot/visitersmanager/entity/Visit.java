/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 7:06 PM
 */
package com.ceyentra.springboot.visitersmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    int visitId;

    @ManyToOne
    @JoinColumn(name = "visitor_id")
    Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "visit_card_id")
    VisitorCard visitorCard;

    @ManyToOne
    @JoinColumn(name = "floor_id")
    Floor floor;

    @Column(name = "check_in_date")
    LocalDate checkInDate;

    @Column(name = "check_in_time")
    LocalTime checkInTime;

    @Column(name = "check_out_time")
    LocalTime checkOutTime;

    @Column(name = "reason")
    String reason;

    @Column(name = "status")
    String visitStatus;

}
