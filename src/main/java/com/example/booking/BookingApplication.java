package com.example.booking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point for the Booking application.
 * <p>
 * This class serves as the launching point for the Spring Boot application. It configures the
 * application to exclude the default DataSource auto-configuration, catering to applications
 * that may not require a datasource or wish to configure it differently.
 * </p>
 * <p>
 * Additionally, it defines the OpenAPI documentation for the application, providing a detailed
 * API description through annotations. This facilitates the generation of interactive API
 * documentation using tools like Swagger UI.
 * </p>
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "My API", version = "v1", description = "Description of my API documentation"))
public class BookingApplication {

	/**
	 * Main method serving as the entry point of the application.
	 *
	 * @param args Command line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

}