package com.hyvercode.springday.firebase.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Note {
  private String subject;
  private String content;
  private Map<String, String> data;
  private String image;
}
