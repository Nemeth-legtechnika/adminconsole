import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar


plugins {
	id("org.springframework.boot") version "3.0.6-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("kapt") version "1.7.22" apply false
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.7.22"
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

allprojects {

	group = "com.nemethlegtechnika"
	version = "0.0.1-SNAPSHOT"

	apply {
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
		plugin("org.jetbrains.kotlin.jvm")
		plugin("org.jetbrains.kotlin.plugin.spring")
		plugin("org.jetbrains.kotlin.plugin.allopen")
	}

	java.sourceCompatibility = JavaVersion.VERSION_17

	repositories {
		mavenCentral()
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://repo.spring.io/snapshot") }
		gradlePluginPortal()
	}

	configurations {
		implementation.configure {
			exclude(module = "spring-boot-starter-tomcat")
			exclude(group = "org.apache.tomcat")
		}

		allOpen {
			annotation("jakarta.persistence.Entity")
			annotation("jakarta.persistence.MappedSuperclass")
		}
	}

	tasks {
		withType<KotlinCompile> {
			kotlinOptions {
				freeCompilerArgs = listOf("-Xjsr305=strict")
				jvmTarget = "17"
			}
		}

		withType<Test> {
			useJUnitPlatform()
		}
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.boot:spring-boot-starter-jetty")
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
        developmentOnly("org.springframework.boot:spring-boot-devtools")
		testImplementation("org.springframework.boot:spring-boot-starter-test") {
			exclude(group = "org.mockito", module = "mockito-core")
		}
		testImplementation("io.mockk:mockk:1.13.4")
	}
}
