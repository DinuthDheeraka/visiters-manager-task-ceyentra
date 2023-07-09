/**
 * @author :  Dinuth Dheeraka
 * Created : 7/8/2023 1:51 AM
 */
package com.ceyentra.springboot.visitersmanager.dto.entity;

import com.ceyentra.springboot.visitersmanager.enums.entity.role.SystemUserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SystemUserDTO {

    int systemUserId;

    String userName;

    String password;

    int active;

    String email;

    SystemUserType systemUserType;
}
