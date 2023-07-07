/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 9:40 PM
 */
package com.ceyentra.springboot.visitersmanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "user_id")
    String userId;
}
