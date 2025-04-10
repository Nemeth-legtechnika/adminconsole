
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version Versions.kotlin
	kotlin("plugin.spring") version Versions.kotlin apply false
	kotlin("kapt") version Versions.kotlin apply false
}

allprojects {

	group = "com.nemethlegtechnika"
	version = "0.0.1-SNAPSHOT"

	apply {
		plugin("org.jetbrains.kotlin.jvm")
	}

	java {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(17))
		}
	}

	repositories {
		mavenCentral()
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://repo.spring.io/snapshot") }
		gradlePluginPortal()
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
}
