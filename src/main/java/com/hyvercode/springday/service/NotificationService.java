package com.hyvercode.springday.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.hyvercode.springday.exception.BusinessException;
import com.hyvercode.springday.firebase.model.Note;
import com.hyvercode.springday.firebase.service.FirebaseMessagingService;
import com.hyvercode.springday.helpers.base.EmptyResponse;
import com.hyvercode.springday.helpers.constant.ErrorConstant;
import com.hyvercode.springday.model.request.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {
  private final FirebaseMessagingService firebaseMessagingService;

  public NotificationService(FirebaseMessagingService firebaseMessagingService) {
    this.firebaseMessagingService = firebaseMessagingService;
  }

  public EmptyResponse execute(NotificationRequest notificationRequest, String token) {
    try {
      firebaseMessagingService.sendNotification(Note.builder()
        .subject(notificationRequest.getSubject())
        .content(notificationRequest.getContent())
        .data(notificationRequest.getData())
        .image(notificationRequest.getImage())
        .build(), token);
    } catch (FirebaseMessagingException e) {
      log.info("Firebase log {} ", e.getMessage());
      throw new BusinessException(
        HttpStatus.CONFLICT,
        ErrorConstant.ERROR_CODE_02,
        ErrorConstant.ERROR_MESSAGE_02
      );
    }

    return new EmptyResponse();
  }
}
