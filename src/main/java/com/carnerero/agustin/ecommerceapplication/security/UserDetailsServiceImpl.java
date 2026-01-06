package com.carnerero.agustin.ecommerceapplication.security;


import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepo;

    public UserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @NullMarked
    @Override
    public UserDetails loadUserByUsername(String email) {

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email)
                );

        return new UserDetailsImpl(user);
    }
}
