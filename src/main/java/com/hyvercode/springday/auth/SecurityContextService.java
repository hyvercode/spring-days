package com.hyvercode.springday.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.UUID;

public interface SecurityContextService {
  public UsernamePasswordAuthenticationToken decodeUserToken(String token);
  public AbstractAuthenticationToken decodeServiceApiSecret(String key);
  public String getCurrentUserId();
  public UUID getCurrentUserTokenId();
  public String getCurrentUserPhoneNumber();
  public String getCurrentToken();
  public String getDeviceId();
  public String getCurrentUserEmail();
}
