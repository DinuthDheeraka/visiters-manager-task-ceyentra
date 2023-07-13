package com.ceyentra.springboot.visitersmanager.repository;

import com.ceyentra.springboot.visitersmanager.entity.VisitorCardEntity;
import com.ceyentra.springboot.visitersmanager.enums.VisitorCardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorCardRepository extends JpaRepository<VisitorCardEntity,Integer> {

    List<VisitorCardEntity> findByVisitorCardStatus(VisitorCardStatus status);

    @Query("select v.visitorCardStatus from VisitorCardEntity v where v.cardId = :id")
    VisitorCardStatus findVisitorCardStatusByCardId(@Param("id") int id);
}
