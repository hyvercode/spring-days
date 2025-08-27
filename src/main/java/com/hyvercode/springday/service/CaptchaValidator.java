package com.hyvercode.springday.service;

import com.hyvercode.springday.model.response.CaptchaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaValidator {

  @Value("${google.recaptcha.url}")
  private String recaptchaUrl;

  @Value("${google.recaptcha.secret}")
  private String recaptchaSecret;

  public boolean validateCaptcha(String captchaResponse) {
    RestTemplate restTemplate = new RestTemplate();

    MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
    requestMap.add("secret", recaptchaSecret);
    requestMap.add("response", captchaResponse);

    CaptchaResponse apiResponse = restTemplate.postForObject(recaptchaUrl, requestMap, CaptchaResponse.class);
    if (apiResponse == null) {
      return false;
    }

    return Boolean.TRUE.equals(apiResponse.getSuccess());
  }
}
