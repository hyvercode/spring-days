package com.hyvercode.springday.controller.mail.service;

import com.hyvercode.springday.mail.model.EmailProperties;
import com.hyvercode.springday.mail.service.EmailService;
import com.hyvercode.springday.model.request.EmailTemplateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Message;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
  @Spy
  private EmailService emailService;

  @Mock
  JavaMailSender javaMailSender;

  @Mock
  EmailProperties emailProperties;

  EmailTemplateRequest request;

  public static final String BODY = "Hi John Doe, Your account has been unlocked!";
  public static final String EMAIL_ADDRESS = "john@doe.com";
  public static final String SUBJECT = "Account Unlocked";

  @BeforeEach
  public void setup(){
    emailProperties = new EmailProperties();
    emailProperties.setHostname("smtp.gmail.com");
    emailProperties.setPort(456);
    emailProperties.setUsername("TEST");
    emailProperties.setPassword("TEST");
    emailProperties.setSender("TEST");

    emailService = new EmailService(javaMailSender, emailProperties);

    request = new EmailTemplateRequest();
    request.setEmailCode("TEST");
    request.setSubject(SUBJECT);
    request.setRecipient(EMAIL_ADDRESS);
    request.setMsgBody(BODY);
    request.setAttachment(null);
    initMocks(this);
  }

  @Test
  void testSendEmailTemplate() throws Exception {
    Mockito.doNothing().when(emailService).send(any(Message.class));
    Assertions.assertDoesNotThrow(() -> emailService.sendEmailWithTemplate(BODY, EMAIL_ADDRESS, SUBJECT));
  }
}


