package com.upaio.messagebroker.chassis.producer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.upaio.messagebroker.chassis.producer.command.Producer;
import com.upaio.messagebroker.chassis.producer.model.ObjectMessageDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1")
@Tag(name = "MessageBrokerProducerController",
    description = "Controlador para manejo de message broker")
@Slf4j
public class MessageBrokerProducerController {

  final Producer producer;

  public MessageBrokerProducerController(final Producer producer) {
    this.producer = producer;
  }

  @PostMapping("/produce")
  public ResponseEntity<String> produce(@RequestBody ObjectMessageDTO objectMessageDTO) {
    log.info("Ejecutando controlador: MessageBrokerProducerController con el metodo: produce");
    log.trace("Ejecutando controlador: MessageBrokerProducerController con el metodo: produce, con la siguiente peticion {}", objectMessageDTO);
    return producer.produce(objectMessageDTO);
  }
}
