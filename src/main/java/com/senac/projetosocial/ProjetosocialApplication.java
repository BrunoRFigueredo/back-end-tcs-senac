package com.senac.projetosocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class ProjetosocialApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetosocialApplication.class, args);
	}

}
