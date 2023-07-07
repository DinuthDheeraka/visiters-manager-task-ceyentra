/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 9:40 PM
 */
package com.ceyentra.springboot.visitersmanager.entity;

import com.ceyentra.springboot.visitersmanager.enums.entity.role.SystemUserType;
import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    SystemUser systemUser;

    @Id
    @Column(name = "role")
    SystemUserType systemUserType;
}
