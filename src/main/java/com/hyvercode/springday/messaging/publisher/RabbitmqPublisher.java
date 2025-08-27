package com.hyvercode.springday.messaging.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyvercode.springday.helpers.constant.RabbitmqPublisherConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class RabbitmqPublisher {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Value("${spring.rabbitmq.queue.name}")
  String queueName;

  @Value("${spring.rabbitmq.exchange}")
  String exchange;

  @Value("${spring.rabbitmq.routing-key}")
  String routingKey;


  public void storeAndSend(String header,
                           Object messagePayload) {
    if (messagePayload == null) {
      throw new NullPointerException(RabbitmqPublisherConstants.EXCEPTION_NULL_PAYLOAD_MESSAGE);
    }

    var messageId = UUID.randomUUID();
    try {
      Map<String, String> messageHeader = new HashMap<>();
      messageHeader.put(header, header);
      var message = buildAmqpMessage(messageId, messageHeader, messagePayload);
      var correlationData = new CorrelationData(messageId.toString());
      rabbitTemplate.send(exchange, routingKey, message, correlationData);
    } catch (JsonProcessingException e) {
      log.info(e.getMessage());
    }

  }

  public void storeAndSend(String exchange, String routingKey, Map<String, String> messageHeader,
                           Object messagePayload) throws JsonProcessingException {
    if (messagePayload == null) {
      throw new NullPointerException(RabbitmqPublisherConstants.EXCEPTION_NULL_PAYLOAD_MESSAGE);
    }

    var messageId = UUID.randomUUID();


    var message = buildAmqpMessage(messageId, messageHeader, messagePayload);
    var correlationData = new CorrelationData(messageId.toString());

    rabbitTemplate.send(exchange, routingKey, message, correlationData);

  }


  private Message buildAmqpMessage(UUID messageId, Map<String, String> messageHeader, Object messagePayload)
    throws JsonProcessingException {
    var messageProps = new MessageProperties();
    var payload = objectMapper.writeValueAsString(messagePayload);

    messageProps.setCorrelationId(messageId.toString());
    messageProps.setHeader(RabbitmqPublisherConstants.HEADER_MESSAGE_ID, messageId);
    Optional.ofNullable(messageHeader).ifPresent(map -> map.forEach(messageProps::setHeader));

    return new Message(Optional.ofNullable(payload).orElse(StringUtils.EMPTY).getBytes(), messageProps);
  }

  public void storeAndSend(String exchange, String routingKey, Object messagePayload) throws JsonProcessingException {
    this.storeAndSend(exchange, routingKey, null, messagePayload);
  }
}
