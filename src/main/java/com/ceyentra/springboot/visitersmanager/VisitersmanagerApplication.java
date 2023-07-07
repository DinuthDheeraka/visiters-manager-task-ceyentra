package com.ceyentra.springboot.visitersmanager;

import com.ceyentra.springboot.visitersmanager.dao.VisitorCardDAO;
import com.ceyentra.springboot.visitersmanager.entity.VisitorCard;
import com.ceyentra.springboot.visitersmanager.enums.entity.visitorcard.VisitorCardStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VisitersmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisitersmanagerApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(VisitorCardDAO cardDAO){

		return runner -> {
			System.out.println(cardDAO.findById(1));
		};
	}
}
