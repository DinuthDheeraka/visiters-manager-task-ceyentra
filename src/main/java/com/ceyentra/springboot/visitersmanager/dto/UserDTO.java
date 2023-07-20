/**
 * @author :  Dinuth Dheeraka
 * Created : 7/13/2023 9:41 PM
 */
package com.ceyentra.springboot.visitersmanager.dto;

import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    Integer id;

    String firstname;

    String lastname;

    String email;

    String password;

    UserRole role;
}
