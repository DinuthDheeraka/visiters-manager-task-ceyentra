/**
 * @author :  Dinuth Dheeraka
 * Created : 7/7/2023 9:35 PM
 */
package com.ceyentra.springboot.visitersmanager.entity;

import com.ceyentra.springboot.visitersmanager.enums.entity.role.SystemUserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
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

    @Column(name = "user_role")
    SystemUserRole systemUserRole;


    public SystemUser(String userName, String password,
                      int active, String email, SystemUserRole systemUserRole) {
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.email = email;
        this.systemUserRole = systemUserRole;
    }

    @Override
    public String toString() {
        return "SystemUser{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", email='" + email + '\'' +
                '}';
    }
}
