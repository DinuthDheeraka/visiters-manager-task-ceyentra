package com.ceyentra.springboot.visitersmanager.repository;

import com.ceyentra.springboot.visitersmanager.entity.FloorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorRepository extends JpaRepository<FloorEntity,Integer> {

    @Query(value = "UPDATE floor f SET f.db_status = :db_status WHERE f.floor_id = :floor_id",nativeQuery = true)
    @Modifying
    int setFloorDbStatusById(@Param("db_status") String db_status,@Param("floor_id") int floor_id);

    @Query(value = "SELECT * FROM floor f WHERE f.db_status = :status",nativeQuery = true)
    List<FloorEntity> selectFloorsByDbStatus(@Param("status")String status);

//    @Query(value = "SELECT db_status FROM floor WHERE floor_id = :id",nativeQuery = true)
//    String findDbStatusByFloorId(@Param("id") int id);
}
