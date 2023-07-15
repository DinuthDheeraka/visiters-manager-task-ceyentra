/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 7:02 PM
 */
package com.ceyentra.springboot.visitersmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "visitor")
public class VisitorEntity {

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

    @OneToMany(
            mappedBy = "visitor",
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    List<VisitEntity> visitList;

    @Override
    public String toString() {
        return "Visitor{" +
                "visitorId=" + visitorId +
                ", nic='" + nic + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
