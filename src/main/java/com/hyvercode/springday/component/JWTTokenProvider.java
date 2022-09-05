package com.hyvercode.springday.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.hyvercode.springday.helpers.constant.SecurityConstants;
import com.hyvercode.springday.model.entity.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.hyvercode.springday.helpers.constant.SecurityConstants.*;
import static java.util.Arrays.stream;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

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

  public String generateJwtToken(UserPrincipal userPrincipal) {
    String[] claims = getClaimsFromUser(userPrincipal);
    return JWT.create()
      .withIssuer(tokenIssuer)
      .withAudience(internalServiceName)
      .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
      .withClaim(SecurityConstants.TOKEN_ROLES_CLAIM_KEY, Collections.singletonList(userPrincipal.getRoles()))
      .withArrayClaim(AUTHORITIES, claims).withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
      .sign(Algorithm.HMAC512("SECRET"));
  }


  // permit to get authorities from the token
  public List<GrantedAuthority> getAuthorities(String token) {
    String[] claims = getClaimsFromToken(token);
    return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
    UsernamePasswordAuthenticationToken userPasswordAuthToken = new
      UsernamePasswordAuthenticationToken(username, null, authorities);
    // set details of users in spring security details
    userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    return userPasswordAuthToken;
  }

  public boolean isTokenValid(String username, String token) {
    JWTVerifier verifier = getJWTVerifier();
    return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);
  }

  public String getSubject(String token) {
    JWTVerifier verifier = getJWTVerifier();
    return verifier.verify(token).getSubject();
  }

  private boolean isTokenExpired(JWTVerifier verifier, String token) {
    Date expiration = verifier.verify(token).getExpiresAt();
    return expiration.before(new Date());
  }

  private String[] getClaimsFromToken(String token) {
    JWTVerifier verifier = getJWTVerifier();
    return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
  }

  private JWTVerifier getJWTVerifier() {
    JWTVerifier verifier;
    try {
      Algorithm algorithm = Algorithm.HMAC512(secret);
      verifier = JWT.require(algorithm).withIssuer(internalServiceName).build();
    }catch (JWTVerificationException exception) {
      throw new JWTVerificationException("Invalid token");
    }
    return verifier;
  }

  private String[] getClaimsFromUser(UserPrincipal user) {
    List<String> authorities = new ArrayList<>();
    for (GrantedAuthority grantedAuthority : user.getAuthorities()){
      authorities.add(grantedAuthority.getAuthority());
    }
    return authorities.toArray(new String[0]);
  }
}
