
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

val mockkVersion: String by properties
val dotenvVersion: String by properties
val kotlinLoggerVersion: String by properties
val antlrVersion: String by properties

plugins {
	kotlin("kapt")
	kotlin("jvm")
	kotlin("plugin.spring")
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	id("org.jetbrains.kotlin.plugin.allopen")
	id("org.sonarqube")
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
		plugin("org.sonarqube")
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

		sonar {
			properties {
				property("sonar.projectKey", "Nemeth-legtechnika_adminconsole")
				property("sonar.organization", "nemeth-legtechnika")
				property("sonar.host.url", "https://sonarcloud.io")
				property("sonar.coverage.jacoco.xmlReportPaths", "${project(":products").buildDir}/reports/kover/report.xml")
			}
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
		implementation("org.antlr:antlr4-runtime:$antlrVersion")
		implementation("org.springframework.boot:spring-boot-starter-jetty")
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.springframework.boot:spring-boot-starter-actuator")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("io.github.oshai:kotlin-logging-jvm:$kotlinLoggerVersion")
		implementation("me.paulschwarz:spring-dotenv:$dotenvVersion")
        developmentOnly("org.springframework.boot:spring-boot-devtools")
		testImplementation("org.springframework.boot:spring-boot-starter-test") {
			exclude(group = "org.mockito", module = "mockito-core")
		}
		testImplementation("io.mockk:mockk:$mockkVersion")
	}
}
