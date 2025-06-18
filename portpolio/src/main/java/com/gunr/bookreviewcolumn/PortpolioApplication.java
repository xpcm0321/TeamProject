package com.gunr.bookreviewcolumn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PortpolioApplication {
	public static void main(String[] args) {
		SpringApplication.run(PortpolioApplication.class, args);
	}

}
