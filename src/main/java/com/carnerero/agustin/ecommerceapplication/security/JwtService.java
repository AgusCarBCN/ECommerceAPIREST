package com.carnerero.agustin.ecommerceapplication.security;


import com.carnerero.agustin.ecommerceapplication.dtos.requests.TokenRequestDTO;
import com.carnerero.agustin.ecommerceapplication.exception.user.UserNotFoundException;
import com.carnerero.agustin.ecommerceapplication.model.entities.RefreshTokenEntity;
import com.carnerero.agustin.ecommerceapplication.model.entities.UserEntity;
import com.carnerero.agustin.ecommerceapplication.repository.RefreshTokenRepository;
import com.carnerero.agustin.ecommerceapplication.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.convert.ValueConverter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Value("${JWT_SECRET}")
    private String secretKey;
    @Value("${JWT_EXPIRATION}")
    private Long accessTokenExpiration;
    @Value("${JWT_REFRESH_EXPIRATION}")
    private Long refreshTokenExpiration;


    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails,accessTokenExpiration);
    }
    public String generateRefreshToken(UserDetails userDetails) {

        // Buscar al usuario
        var user = userRepository.findByEmailWithRoles(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Buscar refresh token existente
        var refreshToken = refreshTokenRepository.findByUserId(user.getId()).orElse(null);

        // Si no existe token, crear uno nuevo
        if (refreshToken == null) {
            String newToken = generateToken(userDetails, refreshTokenExpiration);
            Instant expiryDate = Instant.now().plusMillis(refreshTokenExpiration);

            refreshToken = RefreshTokenEntity.builder()
                    .user(user)
                    .token(newToken)
                    .expiryDate(expiryDate)
                    .build();

            refreshTokenRepository.save(refreshToken);
            return newToken;
        }

        // Si el token existe, revisar si está expirado
        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            // Token vencido → generar nuevo
            String newToken = generateToken(userDetails, refreshTokenExpiration);
            Instant expiryDate = Instant.now().plusMillis(refreshTokenExpiration);

            refreshToken.setToken(newToken);
            refreshToken.setExpiryDate(expiryDate);
            refreshTokenRepository.save(refreshToken);

            return newToken;
        }

        // Token aún válido → devolver el existente
        return refreshToken.getToken();
    }


    // Generar token JWT
    public String generateToken(UserDetails userDetails,Long expiration) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public List<String> extractRoles(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return ((List<?>) claims.get("roles")).stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expirado, vuelve a hacer login");
        }
        return token;
    }
    public RefreshTokenEntity findRefreshToken(TokenRequestDTO tokenRequestDTO) {
        var refreshTokenEntity=  refreshTokenRepository.findByToken(tokenRequestDTO.getToken()).orElseThrow(()->new UserNotFoundException("Invalid refresh token"));
                 new RuntimeException("Invalid refresh token");
                 return refreshTokenEntity;


    }


}
