package com.hyvercode.springday.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ErrorResponse implements Serializable {
  private String code;
  private String title;
  private String message;
}
