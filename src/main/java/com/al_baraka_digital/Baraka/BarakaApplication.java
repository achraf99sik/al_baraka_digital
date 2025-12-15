package com.al_baraka_digital.Baraka;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BarakaApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

		String postgresUri = String.format(
				"jdbc:postgresql://%s:%s/%s?sslmode=require&channel_binding=require",
				dotenv.get("PG_HOST"),
				dotenv.get("PG_PORT"),
				dotenv.get("PG_DB")
		);

		System.setProperty("spring.datasource.url", postgresUri);
		System.setProperty("spring.datasource.username", dotenv.get("PG_USERNAME"));
		System.setProperty("spring.datasource.password", dotenv.get("PG_PASSWORD"));
		SpringApplication.run(BarakaApplication.class, args);
	}

}
