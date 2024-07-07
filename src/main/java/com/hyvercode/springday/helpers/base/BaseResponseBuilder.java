package com.hyvercode.springday.helpers.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class BaseResponseBuilder<T> extends BaseResponse {

  protected String code;
  protected String status;
  protected transient T content;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  protected LocalDateTime localDateTime;

  public BaseResponseBuilder() {}

  public BaseResponseBuilder(String code, String status, T content, LocalDateTime localDateTime) {
    this.code = code;
    this.status = status;
    this.content = content;
    this.localDateTime = localDateTime;
  }

  public BaseResponseBuilder(String code, String status, T content) {
    this.code = code;
    this.status = status;
    this.content = content;
    this.localDateTime = LocalDateTime.now();
  }

  public BaseResponseBuilder(String code, String status) {
    this.code = code;
    this.status = status;
    this.localDateTime = LocalDateTime.now();
  }
}
