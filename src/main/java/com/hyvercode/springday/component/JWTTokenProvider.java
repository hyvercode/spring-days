package com.hyvercode.springday.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.hyvercode.springday.model.entity.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

import static com.hyvercode.springday.helpers.constant.SecurityConstants.*;

@Component
public class JWTTokenProvider {


  @Value("${setting.jwt.token.issuer}")
  private String secret;

  @Value("${setting.jwt.token.issuer}")
  private String tokenIssuer;

  @Value("${setting.service.internal.key}")
  private String internalServiceSecret;

  @Value("${setting.service.internal.name}")
  private String internalServiceName;

  private Algorithm algorithm;

  public JWTTokenProvider(Algorithm algorithm) {
    this.algorithm = algorithm;
  }

  public String generateJwtToken(UserPrincipal userPrincipal) {
    String[] claims = getClaimsFromUser(userPrincipal);

    return JWT.create()
      .withIssuer(tokenIssuer)
      .withIssuedAt(new Date())
      .withSubject(userPrincipal.getUsername())
      .withClaim("roles", userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
      .withArrayClaim(AUTHORITIES, claims).withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
      .sign(algorithm);
  }

  public String refreshJwtToken(UserPrincipal userPrincipal) {
    return JWT.create()
      .withIssuer(tokenIssuer)
      .withSubject(userPrincipal.getUsername())
      .withExpiresAt(new Date(System.currentTimeMillis()+ 8 * 60*60*1000))
      .sign(algorithm);
  }

  private String[] getClaimsFromUser(UserPrincipal user) {
    List<String> authorities = new ArrayList<>();
    for (GrantedAuthority grantedAuthority : user.getAuthorities()){
      authorities.add(grantedAuthority.getAuthority());
    }
    return authorities.toArray(new String[0]);
  }
}
