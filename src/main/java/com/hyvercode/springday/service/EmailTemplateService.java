package com.hyvercode.springday.service;

import com.hyvercode.springday.mail.config.TemplatingEngine;
import com.hyvercode.springday.mail.model.EmailProperties;
import com.hyvercode.springday.mail.service.EmailService;
import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.model.entity.EmailTemplate;
import com.hyvercode.springday.model.request.EmailTemplateRequest;
import com.hyvercode.springday.repository.EmailTemplateRepository;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EmailTemplateService {

  private final EmailTemplateRepository emailTemplateRepository;
  private final EmailService emailService;
  TemplatingEngine templatingEngine;

  EmailProperties emailProperties;

  public EmailTemplateService(EmailTemplateRepository emailTemplateRepository, EmailService emailService, TemplatingEngine templatingEngine, EmailProperties emailProperties) {
    this.emailTemplateRepository = emailTemplateRepository;
    this.emailService = emailService;
    this.templatingEngine = templatingEngine;
    this.emailProperties = emailProperties;
  }

  public EmptyResponse execute(EmailTemplateRequest request) {
    return emailTemplateRepository.findByEventCode(request.getEventCode())
      .map(emailTemplate -> this.doExecute(request,emailTemplate))
      .orElseThrow(() -> {
        throw new BusinessException(
          HttpStatus.CONFLICT,
          "80000",
          "email template not found",
          "email template not found for eventCode: " + request.getEventCode()
        );
      });
  }

  private EmptyResponse doExecute(EmailTemplateRequest request, EmailTemplate emailTemplate) {
    var emailAddress = request.getRecipient();
    var title = request.getSubject();

    log.info("Content {}",emailTemplate.getEnContent());

    var body = emailTemplate.getEnContent();
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("subject",request.getSubject());
    parameters.put("from",request.getRecipient());
    parameters.put("body",request.getMsgBody());
    parameters.put("date",new Date());
    var enrichedTemplate = templatingEngine.transform(body, parameters);

    this.sendEmailAndLog(emailAddress, title, enrichedTemplate);
    return new EmptyResponse();
  }

  private void sendEmailAndLog(
    String emailAddress,
    String title,
    String enrichedTemplate
  ) {
    Try.run(() -> {
      emailService.sendEmailWithTemplate(enrichedTemplate, emailAddress, title);
      log.info("[+] email sent to {}", emailAddress);
    }).onFailure(e -> log.error("[x] failed to send email with error: {}",e.getMessage()));
  }
}
