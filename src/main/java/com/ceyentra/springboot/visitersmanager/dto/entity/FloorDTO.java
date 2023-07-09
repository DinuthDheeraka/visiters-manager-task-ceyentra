/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 7:00 PM
 */
package com.ceyentra.springboot.visitersmanager.dto.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class FloorDTO {

    int floorId;

    String floorNumber;

    String floorName;

    public FloorDTO(int floorId, String floorNumber, String floorName) {
        this.floorId = floorId;
        this.floorNumber = floorNumber;
        this.floorName = floorName;
    }
}
