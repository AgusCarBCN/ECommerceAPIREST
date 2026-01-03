package com.carnerero.agustin.ecommerceapplication;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserAddressRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.model.enums.AddressType;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserQueryService;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserRegistrationService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
public class ECommerceApplication  implements  CommandLineRunner {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRegistrationService useCase;


    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }


   @Override
    public void run(String... args) throws Exception {
        /*var addressDTO= UserAddressRequestDTO.builder()
                .state("Catalunya")
                .addressType(AddressType.HOME)
                .city("Barcelona")
                .country("Spain")
                .isDefault(true)
                .postalCode("08028")
                .street("Bassegoda, 30")
                .build();
        var userRequestDTO= UserRequestDTO.builder()
                .userName("Agustin Carnerero")
                .email("agusticar@gmail.com")
                .password("12131")
                        .addresses(Set.of(addressDTO))
                .build();
        useCase.registerUser(userRequestDTO);*/
    }
}
