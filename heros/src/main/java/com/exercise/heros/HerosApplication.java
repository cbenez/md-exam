package com.exercise.heros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author CRISTIAN
 */

@SpringBootApplication
@EnableCaching
@ComponentScan("com.exercise.heros")
public class HerosApplication {

	public static void main(String[] args) {
		SpringApplication.run(HerosApplication.class, args);
	}

}
