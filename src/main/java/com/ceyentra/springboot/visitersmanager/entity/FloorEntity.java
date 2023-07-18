/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 6:32 PM
 */
package com.ceyentra.springboot.visitersmanager.entity;

import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "floor")
public class FloorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "floor_id")
    int floorId;

    @Column(name = "floor_number")
    String floorNumber;

    @Column(name = "floor_name")
    String floorName;

    @Column(name = "db_status")
    @Enumerated(EnumType.STRING)
    EntityDbStatus dbStatus;

    @OneToMany(
            mappedBy = "floor",
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    List<VisitEntity> visitList;

    @Override
    public String toString() {
        return "Floor{" +
                "floorId=" + floorId +
                ", floorNumber='" + floorNumber + '\'' +
                ", floorName='" + floorName + '\'' +
                '}';
    }
}
