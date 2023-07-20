package com.ceyentra.springboot.visitersmanager.config.config;

import com.ceyentra.springboot.visitersmanager.entity.UserEntity;
import com.ceyentra.springboot.visitersmanager.repository.UserRepository;
import com.ceyentra.springboot.visitersmanager.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;
  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  private final UserService userService;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
//    System.out.println(claims.get("Role"));
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails) {
    //find user base on token
    Optional<UserEntity> user = userService.findByEmail(userDetails.getUsername());

    //extra details
    HashMap<String,Object> hashMap = new HashMap<>();

    if(user.isPresent()){
      hashMap.put("User-Id",user.get().getId());
      hashMap.put("First Name",user.get().getFirstname());
      hashMap.put("Last Name",user.get().getLastname());
      hashMap.put("Email",user.get().getEmail());
      hashMap.put("Role",user.get().getRole());
    }

    return generateToken(hashMap, userDetails);
  }

  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  public String generateRefreshToken(UserDetails userDetails) {

    //find user base on token
    Optional<UserEntity> user = userService.findByEmail(userDetails.getUsername());

    //extra details
    HashMap<String,Object> hashMap = new HashMap<>();

    if(user.isPresent()){
      hashMap.put("User-Id",user.get().getId());
      hashMap.put("First Name",user.get().getFirstname());
      hashMap.put("Last Name",user.get().getLastname());
      hashMap.put("Email",user.get().getEmail());
      hashMap.put("Role",user.get().getRole());
    }

    return buildToken(hashMap, userDetails, refreshExpiration);
  }

  private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
