package com.ceyentra.springboot.visitersmanager.dao;

import com.ceyentra.springboot.visitersmanager.entity.VisitorCard;
import com.ceyentra.springboot.visitersmanager.enums.entity.visitorcard.VisitorCardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorCardDAO extends JpaRepository<VisitorCard,Integer> {

    List<VisitorCard> findByVisitorCardStatus(VisitorCardStatus status);

    @Query("select v.visitorCardStatus from VisitorCard v where v.cardId = :id")
    VisitorCardStatus findVisitorCardStatusByCardId(@Param("id") int id);
}
