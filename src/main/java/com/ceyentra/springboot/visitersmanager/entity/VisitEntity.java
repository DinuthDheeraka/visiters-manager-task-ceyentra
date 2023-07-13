/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 7:06 PM
 */
package com.ceyentra.springboot.visitersmanager.entity;

import com.ceyentra.springboot.visitersmanager.enums.VisitStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "visit")
public class VisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    int visitId;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "visitor_id")
    VisitorEntity visitor;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "visit_card_id")
    VisitorCardEntity visitorCard;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "floor_id")
    FloorEntity floor;

    @Column(name = "check_in_date")
    LocalDate checkInDate;

    @Column(name = "check_in_time")
    LocalTime checkInTime;

    @Column(name = "check_out_time")
    LocalTime checkOutTime;

    @Column(name = "reason")
    String reason;

    @Column(name = "status")
    VisitStatus visitStatus;

    public VisitEntity(LocalDate checkInDate, LocalTime checkInTime,
                       LocalTime checkOutTime, String reason,
                       VisitStatus visitStatus) {
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.reason = reason;
        this.visitStatus = visitStatus;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "visitId=" + visitId +
                ", checkInDate=" + checkInDate +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", reason='" + reason + '\'' +
                ", visitStatus=" + visitStatus +
                '}';
    }
}
