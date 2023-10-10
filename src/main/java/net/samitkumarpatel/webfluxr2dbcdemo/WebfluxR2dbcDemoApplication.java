package net.samitkumarpatel.webfluxr2dbcdemo;

import net.samitkumarpatel.webfluxr2dbcdemo.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableR2dbcAuditing
public class WebfluxR2dbcDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxR2dbcDemoApplication.class, args);
	}

	@Bean
	ReactiveAuditorAware<String> auditorAware() {
		/*
		* TODO In real scenario get the user detail from SecurityContex
		return () -> ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .filter(Authentication::isAuthenticated)
        .map(Authentication::getPrincipal)
        .map(User.class::cast)
        .map(User::getUsername);
		* */
		return () -> Mono.just(new User("1", System.getenv("LOGNAME")).name());
	}

}