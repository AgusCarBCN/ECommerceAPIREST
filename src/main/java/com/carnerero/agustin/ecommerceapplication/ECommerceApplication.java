package com.carnerero.agustin.ecommerceapplication;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.OrderService;
import com.carnerero.agustin.ecommerceapplication.services.interfaces.user.UserQueryService;
import com.carnerero.agustin.ecommerceapplication.util.mapper.OrderMapper;
import com.carnerero.agustin.ecommerceapplication.util.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ECommerceApplication  implements  CommandLineRunner {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ProductMapper orderProductMapper;
    @Autowired
    UserQueryService userQueryService;

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }


   @Override
    public void run(String... args) throws Exception {






    }
}
