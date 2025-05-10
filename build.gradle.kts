
import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.MetricType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version Versions.kotlin
	id("org.jetbrains.kotlinx.kover") version Versions.kover
	kotlin("plugin.spring") version Versions.kotlin apply false
	kotlin("kapt") version Versions.kotlin apply false
}

allprojects {

	group = "com.nemethlegtechnika"
	version = "0.0.1-SNAPSHOT"

	apply {
		plugin("org.jetbrains.kotlin.jvm")
		plugin("org.jetbrains.kotlinx.kover")
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
				isEnabled = false
				bound {
					minValue = 10 //Todo change to 80
					metric = MetricType.LINE
					aggregation = AggregationType.COVERED_PERCENTAGE
				}
			}

			rule("Branch Coverage") {
				isEnabled = false
				bound {
					minValue = 10 //Todo change to 80
					metric = MetricType.BRANCH
				}
			}
		}
	}

	configurations {
		all {
			exclude(module = "spring-boot-starter-tomcat")
			exclude(group = "org.apache.tomcat")
		}
	}
}
