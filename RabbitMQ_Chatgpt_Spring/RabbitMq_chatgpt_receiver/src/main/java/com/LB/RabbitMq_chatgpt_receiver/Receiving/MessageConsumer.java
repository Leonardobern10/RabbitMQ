package com.LB.RabbitMq_chatgpt_receiver.Receiving;

import com.LB.RabbitMq_chatgpt_receiver.config.RabbitMqConfigConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageConsumer (RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMqConfigConsumer.INPUT_QUEUE_NAME )
    public void receiveMessage (String message) {
        System.out.println("Mensagem recebida: '" + message + "'");

        // Processamento necessário
        // Verificar a disponibilidade de estoque
        // Atualizar o banco de dados

        // Publicar a mensagem processada na fila de saída
        rabbitTemplate.convertAndSend( RabbitMqConfigConsumer.EXCHANGE_NAME, RabbitMqConfigConsumer.OUTPUT_ROUTING_KEY, message );
        System.out.println("Mensagem processada e enviada: '" + message + "processedMensagem");
    }

}
