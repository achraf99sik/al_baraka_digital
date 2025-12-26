package com.al_baraka_digital.Baraka;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BarakaApplication {

	public static void main(String[] args) {
		String ssl = "?sslmode=require&channel_binding=require";
		ssl = "";
		String postgresUri = String.format(
				"jdbc:postgresql://%s:%s/%s%s",
				require("PG_HOST"),
				require("PG_PORT"),
				require("PG_DB"),
				ssl
		);

		System.setProperty("spring.datasource.url", postgresUri);
		System.setProperty("spring.datasource.username", require("PG_USERNAME"));
		System.setProperty("spring.datasource.password", require("PG_PASSWORD"));
		SpringApplication.run(BarakaApplication.class, args);
	}
	private static String require(String key) {
		Dotenv env = Dotenv.configure().ignoreIfMissing().load();
		String value = env.get(key);
		if (value == null) {
			throw new IllegalStateException("Missing env variable: " + key);
		}
		return value;
	}


}
