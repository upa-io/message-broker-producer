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
  
  @Bean
  Queue createQueue() {
      return new Queue("myQueue", true);
  }
  
  @Bean
  DirectExchange exchange() {
      return new DirectExchange("myDirectExchange");
  }
  
  @Bean
  Binding binding(final Queue queue, final DirectExchange exchange) {
      return BindingBuilder.bind(queue).to(exchange).with("example");
  }
  
  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
      return new Jackson2JsonMessageConverter();
  }
  
  @Bean
  public ConnectionFactory connectionFactory() {
      return (ConnectionFactory)new CachingConnectionFactory();
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
      return (AmqpAdmin)new RabbitAdmin(this.connectionFactory());
  }
}
