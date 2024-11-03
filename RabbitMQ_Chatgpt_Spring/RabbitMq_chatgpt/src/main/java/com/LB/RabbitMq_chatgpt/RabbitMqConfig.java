package com.LB.RabbitMq_chatgpt;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String QUEUE_NAME = "helloQueue";
    public static final String EXCHANGE_NAME = "helloExchange";
    public static final String ROUTING_KEY = "hello.routingKey";

    @Bean
    public Queue queue () {
        // Declarando uma fila não durável (não sobrevive a uma reinicialização do broker)
        return new Queue (QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange exchange () {
        return new TopicExchange (EXCHANGE_NAME);
    }

    // Vinculando uma fila à uma exchange através da chave de roteamento {hello.routing}
    @Bean
    public Binding binding (Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
