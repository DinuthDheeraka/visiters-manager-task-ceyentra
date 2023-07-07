/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 9:35 PM
 */
package com.ceyentra.springboot.visitersmanager.entity;

import com.ceyentra.springboot.visitersmanager.enums.entity.role.SystemUserType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    int systemUserId;

    @Column(name = "user_name")
    String userName;

    @Column(name = "password")
    String password;

    @Column(name = "active")
    int active;

    @Column(name = "email")
    String email;

    @Column(name = "user_type")
    String systemUserType;


    public SystemUser(String userName, String password,
                      int active, String email, String systemUserType) {
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.email = email;
        this.systemUserType = systemUserType;
    }

    @Override
    public String toString() {
        return "SystemUser{" +
                "systemUserId=" + systemUserId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", email='" + email + '\'' +
                ", systemUserType=" + systemUserType +
                '}';
    }
}
