package com.carnerero.agustin.ecommerceapplication;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserAddressRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.RoleEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserAddressEntity;
import com.carnerero.agustin.ecommerceapplication.model.enums.AddressType;
import com.carnerero.agustin.ecommerceapplication.model.enums.Roles;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserRegistrationService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
public class ECommerceApplication implements CommandLineRunner {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRegistrationService useCase;


    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }

    public void run(String... args) throws Exception {


        var address= UserAddressRequestDTO.builder()
                .street("Bassegoda, 30")
                .city("Barcelona")
                .state("Catalunya")
                .postalCode("08028")
                .country("Spain")
                .addressType(AddressType.HOME)
                .isDefault(true)
                .build();
        var user= UserRequestDTO.builder()
                .userName("Agustin Carnerero")
                .email("agusticar@gmail.com")
                .password("Nina197122$$")
                .addresses(Set.of(address))
                .build();

        var userResponse= useCase.registerUser(user);


        System.out.println("-----user response---");
        System.out.println(userResponse);

    }
}
