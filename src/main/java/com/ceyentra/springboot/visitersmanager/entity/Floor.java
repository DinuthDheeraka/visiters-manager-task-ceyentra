/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 6:32 PM
 */
package com.ceyentra.springboot.visitersmanager.entity;

import jakarta.persistence.*;

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
}
