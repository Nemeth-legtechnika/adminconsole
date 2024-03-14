pluginManagement {
	val kotlinVersion: String by settings
	val springBootVersion: String by settings
	val dependencyManagementVersion: String by settings
	val antlrVersion: String by settings
	val koverVersion: String by settings
	repositories {
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://repo.spring.io/snapshot") }
		gradlePluginPortal()
	}
	plugins {
		kotlin("kapt") version kotlinVersion apply false
		kotlin("jvm") version kotlinVersion
		kotlin("plugin.spring") version kotlinVersion
		id("org.springframework.boot") version springBootVersion
		id("io.spring.dependency-management") version dependencyManagementVersion
		id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
		id("antlr") version antlrVersion
		id("org.jetbrains.kotlinx.kover") version koverVersion
	}
}
rootProject.name = "adminconsole"
include("products")