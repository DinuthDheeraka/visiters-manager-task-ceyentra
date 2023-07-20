package com.ceyentra.springboot.visitersmanager.repository;

import com.ceyentra.springboot.visitersmanager.constant.NativeQueryConstant;
import com.ceyentra.springboot.visitersmanager.entity.VisitEntity;
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

    @Query(value = NativeQueryConstant.SAVE_VISIT, nativeQuery = true)
    @Modifying
    void saveVisit(@Param("visitor_id") int visitor_id, @Param("visitor_card_id") int visitor_card_id,
                   @Param("floor_id") int floor_id, @Param("check_in_date") LocalDate check_in_date,
                   @Param("check_in_time") LocalTime check_in_time, @Param("check_out_time") LocalTime check_out_time,
                   @Param("reason") String reason, @Param("status") String status);

    @Query(
            value = "UPDATE Visit v SET v.visitor_id = :visitor_id , v.visit_card_id = :visit_card_id , v.floor_id = :floor_id , v.check_in_date = :check_in_date , v.check_in_time = :check_in_time , v.check_out_time = :check_out_time , v.reason = :reason , v.status = :status WHERE v.visit_id = :visit_id",
            nativeQuery = true)
    @Modifying
    void patcher(@Param("visit_id") int visit_id, @Param("visitor_id") int visitor_id, @Param("visit_card_id") int visit_card_id,
                 @Param("floor_id") int floor_id, @Param("check_in_date") LocalDate check_in_date,
                 @Param("check_in_time") LocalTime check_in_time, @Param("check_out_time") LocalTime check_out_time,
                 @Param("reason") String reason, @Param("status") String status);


    @Query(value = "SELECT * FROM visit v WHERE v.check_in_date BETWEEN :start AND :end AND db_status = :db_status", nativeQuery = true)
    List<VisitEntity> findVisitsByDateRange(@Param("start") String startDate, @Param("end") String endDate, @Param("db_status") String db_status);


    @Query(value = "SELECT * FROM visit v WHERE v.check_in_date<=:end AND db_status = :db_status", nativeQuery = true)
    List<VisitEntity> findVisitsUntilGivenDate(@Param("end") String endDate, @Param("db_status") String db_status);


    @Query(value = "SELECT * FROM visit v WHERE v.db_status = :db_status", nativeQuery = true)
    List<VisitEntity> findAllVisitsByDbStatus(@Param("db_status") String db_status);


    @Query(value = "UPDATE visit v SET v.db_status = :status WHERE v.visit_id = :id", nativeQuery = true)
    @Modifying
    int updateVisitDbStatusById(@Param("status") String status, @Param("id") int id);

}
