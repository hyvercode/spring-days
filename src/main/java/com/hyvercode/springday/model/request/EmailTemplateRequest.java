package com.hyvercode.springday.model.request;

import com.hyvercode.springday.helpers.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailTemplateRequest extends BaseRequest {

  private String emailCode;
  private String recipient;
  private String msgBody;
  private String subject;
  private String attachment;
}
