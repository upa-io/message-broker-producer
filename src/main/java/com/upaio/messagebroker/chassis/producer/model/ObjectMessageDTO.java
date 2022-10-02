package com.upaio.messagebroker.chassis.producer.model;

import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(name = "ObjectMessageDTO")
public class ObjectMessageDTO implements Serializable {
  
  @Serial
  private static final long serialVersionUID = 9091314113317626180L;
  private String message;
  private String anotherMessage;

}
