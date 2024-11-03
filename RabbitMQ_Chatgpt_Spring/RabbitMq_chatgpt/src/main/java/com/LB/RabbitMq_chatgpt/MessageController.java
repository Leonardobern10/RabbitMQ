package com.LB.RabbitMq_chatgpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final MessageProducer messageProducer;

    @Autowired
    public MessageController (MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @GetMapping("/send")
    public String sendHello () {
        String message = "Hello World!";
        messageProducer.sendMessage( message );
        return "Mensagem '" + message + "' enviada";
    }
}
