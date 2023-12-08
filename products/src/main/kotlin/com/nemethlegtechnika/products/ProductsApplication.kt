package com.nemethlegtechnika.products

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(
	info = Info(
		title = "Admin-console backend",
		version = "0.0.1-SNAPSHOT",
		description = "Backend application for Németh Légtechnika Kft.",
		contact = Contact(
			name = "Németh Botond",
			email = "nemethbotond.sopron@gmail.com",
			url = "https://github.com/Zsupi"
		)
	)
)
class ProductsApplication

fun main(args: Array<String>) {
	runApplication<ProductsApplication>(*args)
}
