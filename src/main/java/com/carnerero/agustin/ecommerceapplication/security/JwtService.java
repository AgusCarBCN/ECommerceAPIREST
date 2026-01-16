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
        var user=userRepository.findByEmail(userDetails.getUsername()).orElseThrow(()->new UserNotFoundException("User not found"));
        var refreshToken=generateToken(userDetails,refreshTokenExpiration);
        var refreshEntity= RefreshTokenEntity.builder()
                .expiryDate(Instant.now().plusMillis(refreshTokenExpiration))
                .token(refreshToken)
                .user(user)
                .build();
        refreshTokenRepository.save(refreshEntity);
        return refreshToken;
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
        // Generar nuevo access token
        //UserDetails userDetails =re.loadUserByUsername(refreshTokenEntity.getUser().getEmail());
        //String newAccessToken = jwtService.generateAccessToken(userDetails);

    }


}
