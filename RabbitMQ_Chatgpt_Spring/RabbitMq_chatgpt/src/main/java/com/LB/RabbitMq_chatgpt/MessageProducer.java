package com.LB.RabbitMq_chatgpt;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducer (RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage (String message) {
        rabbitTemplate.convertAndSend( RabbitMqConfig.EXCHANGE_NAME, RabbitMqConfig.ROUTING_KEY, message );
        System.out.println("Mensagem enviada: " + message);
    }
}
