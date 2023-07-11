package com.ceyentra.springboot.visitersmanager.dao;

import com.ceyentra.springboot.visitersmanager.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorDAO extends JpaRepository<Visitor, Integer> {

    @Query("from Visitor v join fetch v.visitList where v.visitorId=:id")
    Visitor findVisitsByVisitorId(@Param("id") int id);
}
