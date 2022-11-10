package com.hyvercode.springday.controller.mail.service;

import com.hyvercode.springday.mail.model.EmailProperties;
import com.hyvercode.springday.mail.service.EmailService;
import com.hyvercode.springday.model.request.EmailTemplateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
  @InjectMocks
  private EmailService emailService;

  @Mock
  JavaMailSender javaMailSender;

  EmailProperties emailProperties;

  EmailTemplateRequest request;

  public static final String BODY = "Hi John Doe, Your account has been unlocked!";
  public static final String EMAIL_ADDRESS = "john@doe.com";
  public static final String SUBJECT = "Account Unlocked";

  @BeforeEach
  public void setup() {
    emailProperties = new EmailProperties();
    emailProperties.setHostname("smtp.gmail.com");
    emailProperties.setPort(456);
    emailProperties.setUsername("TEST");
    emailProperties.setPassword("TEST");
    emailProperties.setSender("TEST");

    emailService = new EmailService(javaMailSender,emailProperties);

    request = new EmailTemplateRequest();
    request.setEmailCode("TEST");
    request.setSubject(SUBJECT);
    request.setRecipient(EMAIL_ADDRESS);
    request.setMsgBody(BODY);
  }

  @Test
  void testSendEmail() throws Exception {
//    Mockito.doNothing().when(emailService).send(any(Message.class));
//    Assertions.assertDoesNotThrow(() -> emailService.sendMail(request));
//
//    verify(emailService).send(any(Message.class));
  }
}


