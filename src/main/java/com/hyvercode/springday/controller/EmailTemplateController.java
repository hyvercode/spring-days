package com.hyvercode.springday.controller;

import com.hyvercode.springday.helpers.base.BaseResponse;
import com.hyvercode.springday.model.request.EmailTemplateRequest;
import com.hyvercode.springday.service.EmailTemplateService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/emails")
public class EmailTemplateController {

  private final EmailTemplateService emailTemplateService;

  public EmailTemplateController(EmailTemplateService emailTemplateService) {
    this.emailTemplateService = emailTemplateService;
  }

  @PostMapping(value = "/send",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public BaseResponse sendMail(@RequestBody @Valid EmailTemplateRequest request){
    return emailTemplateService.execute(request);
  }
}
