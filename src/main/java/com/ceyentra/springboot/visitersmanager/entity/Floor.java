/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 6:32 PM
 */
package com.ceyentra.springboot.visitersmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "floor")
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "floor_id")
    int floorId;

    @Column(name = "floor_number")
    String floorNumber;

    @Column(name = "floor_name")
    String floorName;

    @OneToMany(
            mappedBy = "floor",
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    List<Visit> visitList;

    public Floor(String floorNumber, String floorName) {
        this.floorNumber = floorNumber;
        this.floorName = floorName;
    }

    public Floor(int floorId, String floorNumber, String floorName) {
        this.floorId = floorId;
        this.floorNumber = floorNumber;
        this.floorName = floorName;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "floorId=" + floorId +
                ", floorNumber='" + floorNumber + '\'' +
                ", floorName='" + floorName + '\'' +
                '}';
    }
}
