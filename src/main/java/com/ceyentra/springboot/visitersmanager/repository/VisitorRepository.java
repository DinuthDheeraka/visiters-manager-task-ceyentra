package com.ceyentra.springboot.visitersmanager.repository;

import com.ceyentra.springboot.visitersmanager.entity.VisitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<VisitorEntity, Integer> {

    @Query("from VisitorEntity v join fetch v.visitList where v.visitorId=:id")
    VisitorEntity findVisitsByVisitorId(@Param("id") int id);

    VisitorEntity findByNic(String nic);
}
