package com.ceyentra.springboot.visitersmanager.dao;

import com.ceyentra.springboot.visitersmanager.entity.VisitorCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorCardDAO extends JpaRepository<VisitorCard,Integer> {

    List<VisitorCard> findByVisitorCardStatus(int status);
}
