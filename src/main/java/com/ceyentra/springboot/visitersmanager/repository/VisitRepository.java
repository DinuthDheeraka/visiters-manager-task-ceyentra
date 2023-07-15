package com.ceyentra.springboot.visitersmanager.repository;

import com.ceyentra.springboot.visitersmanager.entity.VisitEntity;
import com.ceyentra.springboot.visitersmanager.enums.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<VisitEntity, Integer> {

    @Query("from VisitEntity where visitor.visitorId=:id")
    List<VisitEntity> findVisitByVisitorId(@Param("id") int id);

    @Query(value = "SELECT * FROM Visit ORDER BY visit_id DESC LIMIT 1" ,nativeQuery = true)
    VisitEntity findLastVisit();

    @Query(value = "UPDATE visit v SET v.status = :status WHERE v.visit_id = :id",nativeQuery = true)
    VisitEntity updateVisitStatusById(@Param("status") VisitStatus visitStatus,@Param("id") int id);

    @Query(
    value = "INSERT INTO visit (visitor_id,visit_card_id,floor_id,check_in_date,check_in_time,check_out_time,reason,status) VALUES (:visitor_id,:visitor_card_id,:floor_id,:check_in_date,:check_in_time,:check_out_time,:reason,:status)",
    nativeQuery = true)
    @Modifying
    void saveVisit(@Param("visitor_id") int visitor_id, @Param("visitor_card_id") int visitor_card_id,
                   @Param("floor_id") int floor_id, @Param("check_in_date") LocalDate check_in_date,
                   @Param("check_in_time") LocalTime check_in_time, @Param("check_out_time") LocalTime check_out_time,
                   @Param("reason") String reason, @Param("status") VisitStatus status);


    @Query("UPDATE VisitEntity v SET v.visitor.visitorId = :visitor_id , v.visitorCard.cardId = :visitor_card_id , v.floor.floorId = :floor_id , v.checkInDate = :check_in_date , v.checkInTime = :check_in_time , v.checkOutTime = :check_out_time , v.reason = :reason , v.visitStatus = :status WHERE v.visitId = :visit_id")
    @Modifying
    void updateVisit(@Param("visit_id") int visit_id, @Param("visitor_id") int visitor_id, @Param("visitor_card_id") int visitor_card_id,
                     @Param("floor_id") int floor_id, @Param("check_in_date") LocalDate check_in_date,
                     @Param("check_in_time") LocalTime check_in_time, @Param("check_out_time") LocalTime check_out_time,
                     @Param("reason") String reason, @Param("status") VisitStatus status);
}
