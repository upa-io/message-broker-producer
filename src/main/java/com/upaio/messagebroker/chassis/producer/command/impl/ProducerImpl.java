package com.upaio.messagebroker.chassis.producer.command.impl;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.upaio.messagebroker.chassis.producer.command.Producer;
import com.upaio.messagebroker.chassis.producer.model.ObjectMessageDTO;
import lombok.extern.slf4j.Slf4j;

@Service("Producer")
@Slf4j
public class ProducerImpl implements Producer {

  private final RabbitTemplate rabbitTemplate;

  public ProducerImpl(final RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public ResponseEntity<String> produce(ObjectMessageDTO objectMessageDTO) {
    log.info("Ejecutando Comando Producer con el metodo producer");
    ResponseEntity<String> responseEntity = null;
    if (null != objectMessageDTO) {
      try {
        this.rabbitTemplate.convertAndSend("myDirectExchange", "example", objectMessageDTO);
        responseEntity = ResponseEntity.status(HttpStatus.OK).build();
      } catch (AmqpException amqpException) {
        ProducerImpl.log.error("Error: {}", amqpException);
        responseEntity = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
      }
    } else {
      responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return responseEntity;
  }

}
