/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 7:02 PM
 */
package com.ceyentra.springboot.visitersmanager.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "visitor")
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visitor_id")
    int visitorId;

    @Column(name = "nic")
    String nic;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "phone")
    String phone;
}
