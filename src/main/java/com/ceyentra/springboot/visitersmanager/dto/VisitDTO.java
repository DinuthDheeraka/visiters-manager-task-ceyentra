/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 6:59 PM
 */
package com.ceyentra.springboot.visitersmanager.dto;

import com.ceyentra.springboot.visitersmanager.entity.Floor;
import com.ceyentra.springboot.visitersmanager.entity.Visitor;
import com.ceyentra.springboot.visitersmanager.entity.VisitorCard;
import com.ceyentra.springboot.visitersmanager.enums.entity.visitor.VisitStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@ToString
public class VisitDTO {

    int visitId;

    VisitorDTO visitor;

    VisitorCardDTO visitorCard;

    FloorDTO floor;

    LocalDate checkInDate;

    LocalTime checkInTime;

    LocalTime checkOutTime;

    String reason;

    VisitStatus visitStatus;

    public VisitDTO(int visitId, LocalDate checkInDate,
                    LocalTime checkInTime, LocalTime checkOutTime,
                    String reason, VisitStatus visitStatus) {
        this.visitId = visitId;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.reason = reason;
        this.visitStatus = visitStatus;
    }
}
