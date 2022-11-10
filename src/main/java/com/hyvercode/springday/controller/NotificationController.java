package com.hyvercode.springday.controller;

import com.hyvercode.springday.helpers.base.BaseResponse;
import com.hyvercode.springday.model.request.NotificationRequest;
import com.hyvercode.springday.service.NotificationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

  private final NotificationService notificationService;

  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @PostMapping(value = "/send/{token}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public BaseResponse sendNotification(@PathVariable("token") String token, @RequestBody NotificationRequest request) {
    return notificationService.execute(request, token);
  }
}
