package com.hyvercode.springday.mail.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class EmailProperties {
  @Value("${spring.mail.host}")
  private String hostname;
  @Value("${spring.mail.port}")
  private int	port;
  @Value("${spring.mail.username}")
  private String username;
  @Value("${spring.mail.password}")
  private String password;
  @Value("${spring.mail.sender}")
  private String sender;

}
