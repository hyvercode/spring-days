package com.hyvercode.springday.controller.messaging.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyvercode.springday.helpers.constant.RabbitmqPublisherConstants;
import com.hyvercode.springday.messaging.listener.RabbitmqListener;
import com.hyvercode.springday.messaging.service.RabbitmqCommonService;
import com.hyvercode.springday.model.entity.Product;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@ExtendWith(MockitoExtension.class)
class RabbitmqListenerTest {

  @InjectMocks
  private RabbitmqListener productListener;
  @Mock
  private ObjectMapper objectMapper;
  @Mock
  private RabbitmqCommonService rabbitmqCommonService;

  @Mock
  private RabbitTemplate rabbitTemplateMock;

  @Mock
  Message message;

  @BeforeEach
  public void setUp() {
    this.rabbitTemplateMock = Mockito.mock(RabbitTemplate.class);
    this.productListener = new RabbitmqListener(objectMapper,rabbitmqCommonService);
  }

  @Test
  void testListen() throws JsonProcessingException {
    var messageProps = new MessageProperties();
    var payload = objectMapper.writeValueAsString(Product.builder()
      .sku("TES")
      .build()
    );

    Map<String,String> messageHeader = new HashMap<>();
    messageHeader.put("TEST","TEST");

    messageProps.setCorrelationId("TES");
    messageProps.setHeader(RabbitmqPublisherConstants.HEADER_MESSAGE_ID, "TEST");
    Optional.of(messageHeader).ifPresent(map -> map.forEach(messageProps::setHeader));

    message = new Message(Optional.ofNullable(payload).orElse(StringUtils.EMPTY).getBytes(), messageProps);

    assertThatCode(() -> productListener.listen(message)).doesNotThrowAnyException();
  }

}
