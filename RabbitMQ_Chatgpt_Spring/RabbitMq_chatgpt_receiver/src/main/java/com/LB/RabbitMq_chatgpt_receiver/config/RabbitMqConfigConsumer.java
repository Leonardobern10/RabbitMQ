package com.LB.RabbitMq_chatgpt_receiver.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfigConsumer {

    public static final String INPUT_QUEUE_NAME = "helloQueue";
    public static final String OUTPUT_QUEUE_NAME = "outputQueue";
    public static final String EXCHANGE_NAME = "helloExchange";
    public static final String INPUT_ROUTING_KEY = "hello.routingKey";
    public static final String OUTPUT_ROUTING_KEY = "goodbye.routingKey";

    @Bean
    public Queue inputQueue () {
        return new Queue ( INPUT_QUEUE_NAME, false);
    }

    @Bean Queue ouputQueue () { return new Queue ( OUTPUT_QUEUE_NAME, false ); }

    @Bean
    public TopicExchange exchange () {
        return new TopicExchange( EXCHANGE_NAME );
    }

    @Bean
    public Binding bindingInputQueue ( Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to( topicExchange ).with( INPUT_ROUTING_KEY );
    }

    @Bean
    public Binding bindingOutputQueue ( Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to( topicExchange ).with(OUTPUT_ROUTING_KEY);
    }
}
