package com.hyvercode.springday.messaging.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyvercode.springday.helpers.constant.RabbitmqPublisherConstants;
import com.hyvercode.springday.messaging.service.RabbitmqCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class RabbitmqListener {

  private ObjectMapper objectMapper;
  private RabbitmqCommonService rabbitmqCommonService;

  public RabbitmqListener(ObjectMapper objectMapper, RabbitmqCommonService rabbitmqCommonService) {
    this.objectMapper = objectMapper;
    this.rabbitmqCommonService = rabbitmqCommonService;
  }

  @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
  public void listen(Message message) throws IOException {
    log.info(String.format("Received message -> %s", message));
    var idempotencyKey = Optional.ofNullable(
      message
        .getMessageProperties()
        .getHeader(RabbitmqPublisherConstants.HEADER_MESSAGE_ID)
    );

    if (
      idempotencyKey.isPresent() &&
        rabbitmqCommonService.isExistsOnCache(idempotencyKey.get().toString())
    ) {
      return;
    }

    var request = objectMapper.readValue(
      message.getBody(), Object.class
    );

    log.debug("{}:{}", idempotencyKey, request);

    idempotencyKey.ifPresent(key -> rabbitmqCommonService.putOnCache(key.toString()));
  }
}
