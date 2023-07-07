package com.ceyentra.springboot.visitersmanager;

import com.ceyentra.springboot.visitersmanager.dao.SystemUserDAO;
import com.ceyentra.springboot.visitersmanager.entity.SystemUser;
import com.ceyentra.springboot.visitersmanager.enums.entity.role.SystemUserType;
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
	public CommandLineRunner commandLineRunner(SystemUserDAO systemUserDAO){

		return runner -> {
			systemUserDAO.save(new SystemUser(
					"Ten","{noop}1010",
					1,"ten@gmail.com", SystemUserType.ROLE_ADMIN.name()
			));
		};
	}
}
