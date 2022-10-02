package com.upaio.messagebroker.chassis.producer.command;

import org.springframework.http.ResponseEntity;
import com.upaio.messagebroker.chassis.producer.model.ObjectMessageDTO;

public interface Producer {
  
  ResponseEntity<String> produce(ObjectMessageDTO objectMessageDTO);

}
