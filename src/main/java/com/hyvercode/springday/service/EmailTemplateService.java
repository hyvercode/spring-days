package com.hyvercode.springday.service;

import com.hyvercode.springday.mail.config.TemplatingEngine;
import com.hyvercode.springday.mail.model.EmailProperties;
import com.hyvercode.springday.mail.service.EmailService;
import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.model.dto.TimeDepositDto;
import com.hyvercode.springday.model.entity.EmailTemplate;
import com.hyvercode.springday.model.request.EmailTemplateRequest;
import com.hyvercode.springday.repository.EmailTemplateRepository;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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
    return emailTemplateRepository.findByEmailCode(request.getEmailCode())
      .map(emailTemplate -> this.doExecute(request, emailTemplate))
      .orElseThrow(() -> {
        throw new BusinessException(
          HttpStatus.CONFLICT,
          "80000",
          "email template not found",
          "email template not found for eventCode: " + request.getEmailCode()
        );
      });
  }

  private EmptyResponse doExecute(EmailTemplateRequest request, EmailTemplate emailTemplate) {
    var emailAddress = request.getRecipient();
    var title = request.getSubject();

    log.info("Content {}", emailTemplate.getContent());

    List<TimeDepositDto> timeDepositList = new ArrayList<>();
    timeDepositList.add(TimeDepositDto.builder()
      .depositAmount(new BigDecimal("10000"))
      .depositInterestRate(1.6F)
      .periodeFrom("01-01-2022")
      .periodeTo("01-12-2022")
      .certificateNo("TDB908-8-908")
      .interestIncome(new BigDecimal(100000000))
      .build());
    timeDepositList.add(TimeDepositDto.builder()
      .depositAmount(new BigDecimal("50000"))
      .depositInterestRate(10.6F)
      .periodeFrom("01-01-2022")
      .periodeTo("01-12-2022")
      .certificateNo("TDB908-8-9010")
      .interestIncome(new BigDecimal(100000000))
      .build());

    var body = emailTemplate.getContent();
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("subject", request.getSubject());
    parameters.put("toAccountName", request.getRecipient());
    parameters.put("note", request.getMsgBody());
    parameters.put("transactionTime", new Date());
    parameters.put("fromAccountName", "Spring Days");
    parameters.put("fromAccountNumber", "098487817471264");
    parameters.put("timeDepositList", timeDepositList);

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
    }).onFailure(e -> log.error("[x] failed to send email with error: {}", e.getMessage()));
  }
}
