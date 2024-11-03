package com.example.demo.Tut1;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues="hello")
public class Tut1Receiver {

	@RabbitHandler
	public void receive (String received) {
		System.out.println(" [x] Recebido '" + received + "'");
	}
}
