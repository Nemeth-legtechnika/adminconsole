import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.MetricType

plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("kapt")
	id("org.jetbrains.kotlinx.kover") version Versions.kover
	id("org.springframework.boot") version Versions.springBoot
	id("io.spring.dependency-management") version Versions.dependencyManagement
}

configurations {
	all {
		exclude(module = "spring-boot-starter-tomcat")
		exclude(group = "org.apache.tomcat")
	}
}

koverReport {
	filters {
		excludes {
			annotatedBy("jakarta.annotation.Generated")
			annotatedBy("javax.annotation.processing.Generated")
			annotatedBy("jakarta.persistence.Entity")
			annotatedBy("jakarta.persistence.MappedSuperclass")
			annotatedBy("jakarta.persistence.metamodel.StaticMetamodel")
			annotatedBy("org.mapstruct.Mapper")
		}
		excludes {
			packages("com.nemethlegtechnika.products.dto")
			packages("com.nemethlegtechnika.products.mapper")
			packages("com.nemethlegtechnika.products.exception")
		}
	}
	verify {
		rule("Basic Line Coverage") {
			isEnabled = true
			bound {
				minValue = 10 //Todo change to 80
				metric = MetricType.LINE
				aggregation = AggregationType.COVERED_PERCENTAGE
			}
		}

		rule("Branch Coverage") {
			isEnabled = true
			bound {
				minValue = 10 //Todo change to 80
				metric = MetricType.BRANCH
			}
		}
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

dependencies {
	implementation("org.liquibase:liquibase-core")
	implementation("org.postgresql:postgresql")
	implementation("org.mapstruct:mapstruct:${Versions.mapstruct}")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${Versions.openapi}")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-jetty")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.github.oshai:kotlin-logging-jvm:${Versions.kotlinLogger}")
	implementation("me.paulschwarz:spring-dotenv:${Versions.dotenv}")
	implementation("org.antlr:antlr4-runtime:${Versions.antlr}")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.mockito", module = "mockito-core")
	}
	testImplementation("io.mockk:mockk:${Versions.mockk}")
	testImplementation("org.springframework.security:spring-security-test")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	kapt("org.mapstruct:mapstruct-processor:${Versions.mapstruct}")
	compileOnly("org.hibernate.orm:hibernate-jpamodelgen:${Versions.hibernateJpaModelGen}")

}

