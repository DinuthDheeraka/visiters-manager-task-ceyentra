/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 12:38 AM
 */
package com.ceyentra.springboot.visitersmanager.dto.request;

import com.ceyentra.springboot.visitersmanager.enums.entity.visitor.VisitStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpRequestVisitDTO {

    int visitId;

    int visitorId;

    int visitorCardId;

    int floorId;

    LocalDate checkInDate;

    LocalTime checkInTime;

    LocalTime checkOutTime;

    String reason;

    VisitStatus visitStatus;
}
