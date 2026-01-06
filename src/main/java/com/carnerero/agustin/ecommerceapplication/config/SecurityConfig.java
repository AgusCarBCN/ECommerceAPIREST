package com.carnerero.agustin.ecommerceapplication.config;



import com.carnerero.agustin.ecommerceapplication.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())  // APIs REST no necesitan CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/products/**").permitAll()   // cualquiera puede acceder
                        .anyRequest().authenticated()                 // el resto requiere login
                )
                .httpBasic(Customizer.withDefaults()) // Por ahora usamos Basic Auth, luego cambiaremos a JWT
                .build();
    }

    // Bean de AuthenticationManager para usar en login (JWT después)
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig
    ) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
