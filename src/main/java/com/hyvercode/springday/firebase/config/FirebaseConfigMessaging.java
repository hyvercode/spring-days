package com.hyvercode.springday.firebase.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;

@Configuration
public class FirebaseConfigMessaging {
  @Bean
  FirebaseMessaging firebaseMessaging() throws IOException {
    GoogleCredentials googleCredentials = GoogleCredentials.getApplicationDefault();
    FirebaseOptions firebaseOptions = FirebaseOptions
      .builder()
      .setCredentials(googleCredentials)
      .build();
    FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "spring-days");
    return FirebaseMessaging.getInstance(app);
  }
}
