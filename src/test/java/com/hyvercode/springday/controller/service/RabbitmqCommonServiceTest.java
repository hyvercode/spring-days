package com.hyvercode.springday.controller.service;

import com.hyvercode.springday.messaging.service.RabbitmqCommonService;
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
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RabbitmqCommonServiceTest {
  @InjectMocks
  private RabbitmqCommonService rabbitmqCommonService;

  @Mock
  private RabbitTemplate rabbitTemplateMock;

  @Autowired
  private RedisTemplate<String, Boolean> redisTemplateGenericBoolean;

  @Test
  void onCacheTest() {
    Assertions.assertNotNull(rabbitmqCommonService);
  }

  @Test
  void putOnCacheTest() {
    assertThatCode(() -> rabbitmqCommonService.putOnCache("TEST")).doesNotThrowAnyException();
  }
}
