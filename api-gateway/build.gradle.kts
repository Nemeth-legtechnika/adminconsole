plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.jetbrains.kotlinx.kover")
    id("org.springframework.boot") version Versions.springBoot
    id("io.spring.dependency-management") version Versions.dependencyManagement
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway-mvc")
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.github.oshai:kotlin-logging-jvm:${Versions.kotlinLogger}")
    implementation("me.paulschwarz:spring-dotenv:${Versions.dotenv}")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.mockito", module = "mockito-core")
    }
    testImplementation("io.mockk:mockk:${Versions.mockk}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${Versions.springCloud}")
    }
}
