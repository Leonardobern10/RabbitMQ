package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.demo.Tut1.DemoApplicationRunner;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	@Profile("usage_message")
	@Bean
	CommandLineRunner usage () {
		return args -> {
			System.out.println("Este aplicativo usa Perfis para controlar" + 
					" seu comportamento.\n");
			System.out.println("Exemplo de uso: java -jar demo-0.0.1-SNAPSHOT.jar " +
					"--spring.profiles.active=hello-world,sender");
		};
	}
	
	@Profile("!usage_message")
	@Bean
	CommandLineRunner tutorial () {
		return new DemoApplicationRunner();
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);
	}

}
