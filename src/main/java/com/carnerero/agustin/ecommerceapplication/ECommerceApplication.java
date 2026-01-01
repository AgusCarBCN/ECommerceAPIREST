package com.carnerero.agustin.ecommerceapplication;
import com.carnerero.agustin.ecommerceapplication.dtos.requests.UserRequestDTO;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserRegistrationService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

        var user= UserRequestDTO.builder()
                .userName("newUser")
                .email("userdada@gmail.com")
                .password("1")
                .build();
        var userResponse=useCase.registerUser(user);
        System.out.println(userResponse);

    }
}
