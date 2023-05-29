plugins {
	kotlin("kapt") version "1.7.22"
}

dependencies {
	implementation("org.liquibase:liquibase-core")
	implementation("org.postgresql:postgresql")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
	implementation("org.mapstruct:mapstruct:1.5.4.Final")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	kapt("org.mapstruct:mapstruct-processor:1.5.4.Final")
}
