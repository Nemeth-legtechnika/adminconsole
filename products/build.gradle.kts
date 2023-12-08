val mapstructVersion: String by properties
val hibernateJpaModelGenVersion: String by properties
val openapiVersion: String by properties

plugins {
	kotlin("kapt")
}

dependencies {
	implementation("org.liquibase:liquibase-core")
	implementation("org.postgresql:postgresql")
	implementation("org.mapstruct:mapstruct:$mapstructVersion")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openapiVersion")
	kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")
	compileOnly("org.hibernate.orm:hibernate-jpamodelgen:$hibernateJpaModelGenVersion")
}
