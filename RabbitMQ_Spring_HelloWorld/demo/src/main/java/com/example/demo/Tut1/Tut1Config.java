package com.example.demo.Tut1;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"Tut1", "hello-world"})
@Configuration
public class Tut1Config {

    @Bean
    Queue hello() {
		return new Queue ("hello");
	}
	
	@Profile("Receiver")
	@Bean
	Tut1Receiver receiver() {
		return new Tut1Receiver();
	}
	
	@Profile("Sender")
	@Bean
	Tut1Sender sender() {
		return new Tut1Sender();
	}
}
