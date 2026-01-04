package com.carnerero.agustin.ecommerceapplication;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.LoginRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.OrderRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserAddressRequestDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import com.carnerero.agustin.ecommerceapplication.model.enums.AddressType;
import com.carnerero.agustin.ecommerceapplication.model.enums.UserStatus;
import com.carnerero.agustin.ecommerceapplication.repository.OrderRepository;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.OrderService;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserQueryService;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserRegistrationService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.OrderMapper;
import com.carnerero.agustin.ecommerceapplication.util.mapper.OrderProductMapper;
import com.carnerero.agustin.ecommerceapplication.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
public class ECommerceApplication  implements  CommandLineRunner {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderProductMapper orderProductMapper;
    @Autowired
    UserQueryService userQueryService;

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }


   @Override
    public void run(String... args) throws Exception {






    }
}
