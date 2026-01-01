package com.carnerero.agustin.tech_e_commerce;

import com.carnerero.agustin.tech_e_commerce.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.tech_e_commerce.entities.BillEntity;
import com.carnerero.agustin.tech_e_commerce.entities.RoleEntity;
import com.carnerero.agustin.tech_e_commerce.entities.Roles;
import com.carnerero.agustin.tech_e_commerce.entities.UserEntity;
import com.carnerero.agustin.tech_e_commerce.mapper.BillMapper;
import com.carnerero.agustin.tech_e_commerce.mapper.UserMapper;
import com.carnerero.agustin.tech_e_commerce.services.users.CreateUserUseCase;
import com.carnerero.agustin.tech_e_commerce.services.users.DeleteUserUseCase;
import com.carnerero.agustin.tech_e_commerce.services.users.SearchUserByNameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Set;

@SpringBootApplication
public class TechECommerceApplication implements CommandLineRunner {

	@Autowired
	UserMapper userMapper;
	@Autowired
	DeleteUserUseCase useCase;
	public static void main(String[] args) {
		SpringApplication.run(TechECommerceApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

	useCase.delete(9L);





	}
}
