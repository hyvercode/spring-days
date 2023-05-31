package com.hyvercode.springday.helpers.constant;

public class AppConstants {
  private AppConstants() {
  }

  public static final String SPECIAL_CHARACTERS = "^[^<>'\"/;`%]*$";
  public static final String PASS_PATTERN_CHARACTERS = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,30}$";
  public static final String EMAIL_PATTERN = ".+@.+\\.[a-z]+";
}
