
import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.MetricType

val mapstructVersion: String by properties
val hibernateJpaModelGenVersion: String by properties
val openapiVersion: String by properties
val oktaVersion: String by properties

plugins {
	kotlin("kapt")
	id("org.jetbrains.kotlinx.kover")
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
				minValue = 20 //Todo change to 80
				metric = MetricType.LINE
				aggregation = AggregationType.COVERED_PERCENTAGE
			}
		}

		rule("Branch Coverage") {
			isEnabled = true
			bound {
				minValue = 20 //Todo change to 80
				metric = MetricType.BRANCH
			}
		}
	}
}

dependencies {
	implementation("org.liquibase:liquibase-core")
	implementation("org.postgresql:postgresql")
	implementation("org.mapstruct:mapstruct:$mapstructVersion")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openapiVersion")
	implementation("com.okta.spring:okta-spring-boot-starter:$oktaVersion")
	kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")
	compileOnly("org.hibernate.orm:hibernate-jpamodelgen:$hibernateJpaModelGenVersion")
}
