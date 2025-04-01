plugins {
	kotlin("jvm") version "2.1.20"
	kotlin("plugin.spring") version "2.1.20"
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "server.kkw"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	// https://mvnrepository.com/artifact/org.apache.poi/poi
	implementation("org.apache.poi:poi:5.4.0")
	// https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml
	implementation("org.apache.poi:poi-ooxml:5.4.0")
	// https://square.github.io/retrofit/
	implementation ("com.squareup.retrofit2:retrofit:2.11.0")
	// https://square.github.io/okio/
	implementation("com.squareup.okio:okio:3.10.2")
	// https://square.github.io/okhttp/
	implementation("com.squareup.okhttp3:okhttp:4.12.0")
	// https://mvnrepository.com/artifact/com.google.code.gson/gson
	implementation("com.google.code.gson:gson:2.12.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
