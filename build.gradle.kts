plugins {
	java
	id("org.springframework.boot") version "3.2.0-M3"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "dev.lxqtpr"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("junit:junit:4.13.2")
	testImplementation("org.projectlombok:lombok:1.18.28")
	compileOnly("org.projectlombok:lombok")
	implementation("io.minio:minio:8.5.5")
	testImplementation("org.mockito:mockito-core:5.6.0")
	implementation("org.modelmapper:modelmapper:3.2.0")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.1.4")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation("org.springframework.boot:spring-boot-configuration-processor:3.1.4")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
