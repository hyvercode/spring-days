package com.hyvercode.springday.controller.messaging.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyvercode.springday.messaging.publisher.RabbitmqPublisher;
import com.hyvercode.springday.model.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;


@ExtendWith(MockitoExtension.class)
class RabbitmqPublisherTest {
  @InjectMocks
  RabbitmqPublisher rabbitmqPublisher;

  @Mock
  private RabbitTemplate rabbitTemplate;

  @Mock
  private ObjectMapper objectMapper;

  @Mock
  private RabbitTemplate rabbitTemplateMock;

  @BeforeEach
  public void setUp() {
    this.rabbitTemplateMock = Mockito.mock(RabbitTemplate.class);
  }

  @Test
  void storeAndSendTest(){
    assertThatCode(() -> rabbitmqPublisher.storeAndSend("TEST", Product.builder()
      .sku("TES")
      .build())).doesNotThrowAnyException();
  }

  @Test
  void storeAndNullTest(){
    assertThatCode(() -> rabbitmqPublisher.storeAndSend("TEST",null));
  }

  @Test
  void storeAndSend2Test(){
    Map<String,String> messageHeader = new HashMap<>();
    messageHeader.put("TEST","TEST");
    assertThatCode(() -> rabbitmqPublisher.storeAndSend("TEST","TES",messageHeader, Product.builder()
      .sku("TES")
      .build())).doesNotThrowAnyException();
  }

  @Test
  void storeAndSend3Test(){
    assertThatCode(() -> rabbitmqPublisher.storeAndSend("TEST","TES", Product.builder()
      .sku("TES")
      .build())).doesNotThrowAnyException();
  }

  @Test
  void storeAndSendNullTest(){
    assertThatCode(() -> rabbitmqPublisher.storeAndSend("TEST","TES", null));
  }

  @Test
  void storeAndSend2NullTest() throws JsonProcessingException {
    Map<String,String> messageHeader = new HashMap<>();
    messageHeader.put("TEST","TEST");
    assertThatCode(() -> rabbitmqPublisher.storeAndSend("TEST","TES",messageHeader,null));
  }
}
