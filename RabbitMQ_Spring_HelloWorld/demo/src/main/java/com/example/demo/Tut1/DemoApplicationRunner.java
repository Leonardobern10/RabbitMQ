package com.example.demo.Tut1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;

public class DemoApplicationRunner implements CommandLineRunner {

	private int duration;
	
	@Autowired
	private ConfigurableApplicationContext ctx;
	
	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("Pronto... rodando por " + duration + "ms");
		ctx.close();
	}

}
