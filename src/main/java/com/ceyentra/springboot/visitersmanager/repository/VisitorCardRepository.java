package com.ceyentra.springboot.visitersmanager.repository;

import com.ceyentra.springboot.visitersmanager.entity.VisitorCardEntity;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.enums.VisitorCardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorCardRepository extends JpaRepository<VisitorCardEntity,Integer> {

    List<VisitorCardEntity> findByVisitorCardStatusAndDbStatus(VisitorCardStatus status, EntityDbStatus dbStatus);

    @Query("select v.visitorCardStatus from VisitorCardEntity v where v.cardId = :id")
    VisitorCardStatus findVisitorCardStatusByCardId(@Param("id") int id);

    @Query(value = "UPDATE visitor_card v SET v.db_status = :db_status WHERE v.card_id = :id",nativeQuery = true)
    @Modifying
    int updateVisitCardDbStatusById(@Param("db_status") String db_status,@Param("id") int id);

    @Query(value = "SELECT * FROM visitor_card v WHERE v.db_status = :status ",nativeQuery = true)
    List<VisitorCardEntity> findVisitorCardsByDbStatus(@Param("status") String status);

    @Query(value = "UPDATE visitor_card v SET v.card_status = :status WHERE v.card_id = :id",nativeQuery = true)
    @Modifying
    void updateVisitorCardStatusById(@Param("status")String status,@Param("id") int id);
}
