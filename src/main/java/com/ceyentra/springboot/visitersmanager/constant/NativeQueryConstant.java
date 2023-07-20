/**
 * @author :  Dinuth Dheeraka
 * Created : 7/20/2023 3:26 PM
 */
package com.ceyentra.springboot.visitersmanager.constant;

public class NativeQueryConstant {

    public static final String SAVE_VISIT =
            "INSERT INTO visit (visitor_id,visit_card_id,floor_id,check_in_date,check_in_time,check_out_time,reason,status) VALUES (?1,?2,?3,?4,?5,?6,?7,?8)";
}
