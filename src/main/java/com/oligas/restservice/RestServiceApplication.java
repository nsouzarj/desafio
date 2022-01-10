package com.oligas.restservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.SpringServletContainerInitializer;


@SpringBootApplication(scanBasePackages = {"com/oligas/services","com/oligas/config", "com/oligas/controllers", "com/oligas/models", "com/oligas/repository"})
@EnableSpringDataWebSupport
@EntityScan("com/oligas/models")
@EnableJpaRepositories("com/oligas/repository")
/**
 * Serviço inicial da apiRestFull
 */
public class RestServiceApplication extends SpringServletContainerInitializer {
	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}
}
