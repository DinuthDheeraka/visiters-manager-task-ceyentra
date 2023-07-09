package com.ceyentra.springboot.visitersmanager.dao;

import com.ceyentra.springboot.visitersmanager.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitDAO extends JpaRepository<Visit, Integer> {

    @Query("from Visit where visitor.visitorId=:id")
    List<Visit> findVisitByVisitorId(@Param("id") int id);
}
