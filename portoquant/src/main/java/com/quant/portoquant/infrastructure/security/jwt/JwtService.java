package com.quant.portoquant.infrastructure.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails user);
    boolean validateToken(String token, UserDetails userDetails);
    String extractUsername(String token);
}
