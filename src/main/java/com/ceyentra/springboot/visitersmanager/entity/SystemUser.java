/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 9:35 PM
 */
package com.ceyentra.springboot.visitersmanager.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_name")
    String userName;

    @Column(name = "pw")
    String password;

    @Column(name = "active")
    int active;

    @Column(name = "email")
    String email;

    @OneToMany(
            mappedBy = "systemUser",
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    List<SystemUserRole> roleList;
}
