package com.hyvercode.springday.config;

import java.io.FileInputStream;
import java.security.KeyStore;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KeyAlgorithmConfig {

  @Value("${setting.jwt.jks.filePath}")
  private String filePath;

  @Value("${setting.jwt.jks.filePassword}")
  private String filePassword;

  @Value("${setting.jwt.jks.keyName}")
  private String keyName;

  @Value("${setting.jwt.jks.keyPassword}")
  private String keyPassword;

  @Value("${setting.jwt.secret}")
  private String secret;

  @Bean
  @Profile({ "dev", "sit", "uat", "prod" })
  Algorithm getKeyAlgorithm() {
    Algorithm keyAlgorithm = null;

    try (var jksStream = new FileInputStream(filePath)) {
      var keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      keyStore.load(jksStream, StringUtils.isNotBlank(filePassword) ? null : filePassword.toCharArray());

      var privateKey = keyStore.getKey(keyName, keyPassword.toCharArray());

      keyAlgorithm = Algorithm.HMAC512(privateKey.getEncoded());
    } catch (Exception e) {
      log.error("Cannot obtain key from certificate", e);
    }

    return keyAlgorithm;
  }

  @Bean
  @Profile({ "local", "local-standalone", "test", "unit-test","h2" })
  Algorithm getKeyAlgorithmLocal() {
    return Algorithm.HMAC512(secret);
  }
}
