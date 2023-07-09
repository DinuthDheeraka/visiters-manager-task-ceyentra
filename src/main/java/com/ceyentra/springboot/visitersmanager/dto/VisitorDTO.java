/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 6:54 PM
 */
package com.ceyentra.springboot.visitersmanager.dto;

import com.ceyentra.springboot.visitersmanager.entity.Visit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VisitorDTO {

    int visitorId;

    String nic;

    String firstName;

    String lastName;

    String phone;
}
