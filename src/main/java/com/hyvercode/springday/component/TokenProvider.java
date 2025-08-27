package com.hyvercode.springday.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hyvercode.springday.helpers.constant.SecurityConstants;
import com.hyvercode.springday.model.entity.Role;
import com.hyvercode.springday.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static com.hyvercode.springday.helpers.constant.SecurityConstants.TOKEN_USER_ID_CLAIM_KEY;

@Component
public class TokenProvider{
  public static final long JWT_TOKEN_VALIDITY = 720 * 60 * 60L; //30d

  @Value("${setting.jwt.token.issuer}")
  private String secret;

  @Value("${setting.jwt.token.issuer}")
  private String tokenIssuer;

  @Value("${setting.service.internal.key}")
  private String internalServiceSecret;

  @Value("${setting.service.internal.name}")
  private String internalServiceName;

  private final Algorithm algorithm;

  public TokenProvider(Algorithm algorithm) {
    this.algorithm = algorithm;
  }

  public String generateToken(User users) {

    String[] roles = users.getRoles().stream().map(Role::getName).toList().toArray(new String[0]);

    return JWT.create()
      .withJWTId(UUID.randomUUID().toString())
      .withIssuer(tokenIssuer)
      .withClaim(TOKEN_USER_ID_CLAIM_KEY, users.getUserId())
      .withClaim(SecurityConstants.TOKEN_DEVICE_ID_CLAIM_KEY, users.getDeviceId())
      .withSubject(users.getUsername())
      .withClaim(SecurityConstants.TOKEN_EMAIL_CLAIM_KEY, users.getEmail())
      .withClaim(SecurityConstants.TOKEN_ROLES_CLAIM_KEY, Arrays.asList(roles))
      .withClaim(SecurityConstants.AuthenticationClaim.SYSTEM_SERVICE.toString(), internalServiceName)
      .withClaim("scope", SecurityConstants.AuthenticationClaim.LOGIN.toString())
      .withKeyId(internalServiceSecret)
      .withIssuedAt(new Date(System.currentTimeMillis()))
      .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
      .sign(algorithm);
  }

}
