package com.hyvercode.springday.component;

import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.model.request.EmailTemplateRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Objects;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@NoArgsConstructor
public class EmailService {

  @Autowired
  private JavaMailSender javaMailSender;

  @Value("${spring.mail.sender}")
  private String sender;


  public EmptyResponse sendMail(EmailTemplateRequest details) {

    // Creating a mime message
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper;

    try {

      mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
      mimeMessageHelper.setFrom(sender);
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
}
