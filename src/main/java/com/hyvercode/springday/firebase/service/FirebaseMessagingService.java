package com.hyvercode.springday.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.hyvercode.springday.firebase.model.Note;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {

  private final FirebaseMessaging firebaseMessaging;

  public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
    this.firebaseMessaging = firebaseMessaging;
  }


  public String sendNotification(Note note, String token) throws FirebaseMessagingException {

    Notification notification = Notification
      .builder()
      .setTitle(note.getSubject())
      .setBody(note.getContent())
      .build();

    Message message = Message
      .builder()
      .setToken(token)
      .setNotification(notification)
      .putAllData(note.getData())
      .build();

    return firebaseMessaging.send(message);
  }
  public String sendNotificationTopic(Note note, String topic) throws FirebaseMessagingException {
    Notification notification = Notification
      .builder()
      .setTitle(note.getSubject())
      .setBody(note.getContent())
      .setImage(note.getImage())
      .build();

    Message message = Message
      .builder()
      .setTopic(topic)
      .setNotification(notification)
      .putAllData(note.getData())
      .build();
    return firebaseMessaging.send(message);
  }
}
