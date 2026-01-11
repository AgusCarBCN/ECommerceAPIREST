package com.carnerero.agustin.ecommerceapplication.security;


import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepo;

    @NullMarked
    @Override
    public UserDetails loadUserByUsername(String email) {

        var userEntity=userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email)
                );

        return UserDetailImpl.builder()
                .user(userEntity)
                .build();
    }
}
