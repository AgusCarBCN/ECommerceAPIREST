package com.carnerero.agustin.ecommerceapplication.config;



import com.carnerero.agustin.ecommerceapplication.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    // Configuración de endpoints y roles
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // APIs REST no necesitan CSRF
               /* .authorizeHttpRequests(auth -> auth
                      /*  .requestMatchers("/admin/**"). hasRole("ADMIN") // solo rol ADMIN
                        .requestMatchers("/user/**"). hasAnyRole("USER","ADMIN") // solo rol USER
                        .requestMatchers("/products/**").permitAll()   // público
                        .requestMatchers("/register/**").permitAll()   // público
                        .requestMatchers("/login").permitAll()
                        .anyRequest().denyAll()                 // resto requiere login
                )*/
                .httpBasic(Customizer.withDefaults()) // Basic Auth por ahora
                .build();
    }

    // AuthenticationManager necesario para login programático
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig
    ) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // PasswordEncoder para validar contraseñas hashadas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
