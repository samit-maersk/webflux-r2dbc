package net.samitkumarpatel.webfluxr2dbcdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class WebfluxR2dbcDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxR2dbcDemoApplication.class, args);
	}

}