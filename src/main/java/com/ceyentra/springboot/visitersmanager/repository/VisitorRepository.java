package com.ceyentra.springboot.visitersmanager.repository;

import com.ceyentra.springboot.visitersmanager.entity.VisitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRepository extends JpaRepository<VisitorEntity, Integer> {

    @Query("from VisitorEntity v join fetch v.visitList where v.visitorId=:id")
    VisitorEntity findVisitsByVisitorId(@Param("id") int id);

    VisitorEntity findByNic(String nic);

    @Query(value = "UPDATE visitor v SET v.db_status = :status WHERE v.visitor_id = :id",nativeQuery = true)
    @Modifying
    int updateVisitorDbStatusById(@Param("status")String status,@Param("id") int id);


    @Query(value = "SELECT * FROM visitor v WHERE v.db_status = :status",nativeQuery = true)
    List<VisitorEntity> findVisitorsByDbStatus(@Param("status") String status);
}
