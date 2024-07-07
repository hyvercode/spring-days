package com.hyvercode.springday.mail.service;

import com.hyvercode.springday.mail.model.EmailProperties;
import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.model.request.EmailTemplateRequest;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;


@Log4j2
@Component
@NoArgsConstructor
public class EmailService {
  private JavaMailSender javaMailSender;
  EmailProperties emailProperties;

  @Autowired
  public EmailService(JavaMailSender javaMailSender, EmailProperties emailProperties) {
    this.javaMailSender = javaMailSender;
    this.emailProperties = emailProperties;
  }

  public EmptyResponse sendMail(EmailTemplateRequest details) {

    // Creating a mime message
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper;

    try {

      mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
      mimeMessageHelper.setFrom(emailProperties.getSender());
      mimeMessageHelper.setTo(details.getRecipient());
      mimeMessageHelper.setText(details.getMsgBody());
      mimeMessageHelper.setSubject(details.getSubject());

      // Adding the attachment
      if (details.getAttachment() != null) {
        FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
        mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
      }

      // Sending the mail
      javaMailSender.send(mimeMessage);

      return new EmptyResponse();

    } catch (Exception e) {
      log.info(e.getMessage());
      throw new BusinessException(
        HttpStatus.BAD_GATEWAY,
        "80008",
        "Failed Send mail",
        "Failed Send mail : " + details.getRecipient()
      );
    }
  }

  public CompletableFuture<Boolean> sendEmailWithTemplate(
    String body,
    String emailAddress,
    String subject
  ) throws MessagingException, UnsupportedEncodingException {

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", emailProperties.getHostname());
    props.put("mail.smtp.port", emailProperties.getPort());


    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
      @Override
      public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(emailProperties.getUsername(), emailProperties.getPassword());
      }
    });

    Message message = this.getEmailMessage(session, subject, emailAddress);
    Multipart multipart = new MimeMultipart();
    this.addBody(addContent(body), multipart);

    message.setContent(multipart);

    this.send(message);
    return CompletableFuture.completedFuture(true);
  }

  public void send(Message message) throws MessagingException {
    Transport.send(message);
  }

  private Message getEmailMessage(
    Session session,
    String subject,
    String recipient
  ) throws UnsupportedEncodingException, MessagingException {

    Message msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress(emailProperties.getSender(), emailProperties.getSender()));

    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
    msg.setSubject(subject);
    msg.setSentDate(new Date());

    return msg;
  }

  private void addBody(MimeBodyPart mimeBodyPart, Multipart multipart) throws MessagingException {
    multipart.addBodyPart(mimeBodyPart);
  }

  private MimeBodyPart addContent(String contentBody) throws javax.mail.MessagingException {
    MimeBodyPart messageBodyPart = new MimeBodyPart();
    messageBodyPart.setContent(contentBody, "text/html");
    return messageBodyPart;
  }
}
