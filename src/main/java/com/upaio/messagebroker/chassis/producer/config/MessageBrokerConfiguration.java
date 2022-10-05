package com.upaio.messagebroker.chassis.producer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageBrokerConfiguration{

  public static final String QUEUE_NAME = "myQueue";
  public static final String EXCHANGE_NAME = "myDirectExchange";
  public static final String ROUTING_KEY = "example";

  public static final String DEAD_LETTER_QUEUE_NAME = "myQueue.dead-letter.queue";
  
  @Bean
  Queue createQueue() {
      return QueueBuilder.durable(QUEUE_NAME)
              .withArgument("x-dead-letter-exchange", "")
              .withArgument("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_NAME)
              .build();
  }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE_NAME).build();
    }
  
  @Bean
  DirectExchange exchange() {
      return new DirectExchange(EXCHANGE_NAME);
  }
  
  @Bean
  Binding binding() {
      return BindingBuilder.bind(createQueue()).to(exchange()).with(ROUTING_KEY);
  }
  
  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
      return new Jackson2JsonMessageConverter();
  }
  
  @Bean
  public ConnectionFactory connectionFactory() {
      return new CachingConnectionFactory();
  }
  
  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
      final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
      rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
      rabbitTemplate.setReplyTimeout(3000L);
      return rabbitTemplate;
  }
  
  @Bean
  public AmqpAdmin amqpAdmin() {
      return new RabbitAdmin(connectionFactory());
  }
}
