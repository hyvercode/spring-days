package com.hyvercode.springday.controller.service;

import com.hyvercode.springday.messaging.listener.ProductListener;
import com.hyvercode.springday.messaging.service.RabbitmqCommonService;
import com.hyvercode.springday.model.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@ExtendWith(MockitoExtension.class)
class RabbitmqCommonServiceTest {
  @InjectMocks
  private RabbitmqCommonService rabbitmqCommonService;

  @Mock
  private RabbitTemplate rabbitTemplateMock;

  @Autowired
  private RedisTemplate<String, Boolean> redisTemplateGenericBoolean;

  @BeforeEach
  public void setUp() {
    this.rabbitTemplateMock = Mockito.mock(RabbitTemplate.class);
  }

  @Test
  void onCacheTest(){
    Assertions.assertNotNull(rabbitmqCommonService);
  }
}
