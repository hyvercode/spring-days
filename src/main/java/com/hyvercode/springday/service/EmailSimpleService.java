package com.hyvercode.springday.service;

import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.mail.service.EmailService;
import com.hyvercode.springday.model.request.EmailTemplateRequest;
import com.hyvercode.springday.repository.EmailTemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSimpleService {

  private final EmailTemplateRepository emailTemplateRepository;
  private final EmailService emailService;

  public EmailSimpleService(EmailTemplateRepository emailTemplateRepository, EmailService emailService) {
    this.emailTemplateRepository = emailTemplateRepository;
    this.emailService = emailService;
  }

  public EmptyResponse execute(EmailTemplateRequest request) {
    return emailTemplateRepository.findByEmailCode(request.getEmailCode())
      .map(titleAndContentOnly -> this.doExecute(request))
      .orElseThrow(() -> {
        throw new BusinessException(
          HttpStatus.CONFLICT,
          "80000",
          "email template not found",
          "email template not found for eventCode: " + request.getEmailCode()
        );
      });
  }

  private EmptyResponse doExecute(EmailTemplateRequest request) {
    return emailService.sendMail(request);
  }
}
