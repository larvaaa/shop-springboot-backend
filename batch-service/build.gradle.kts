plugins {
	kotlin("plugin.jpa")
	kotlin("kapt")
}

dependencies {

	implementation(project(":module-common:common-core"))
	implementation(project(":module-common:common-jpa"))

//	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-batch")
//	implementation("org.springframework.boot:spring-boot-starter-web")
//	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("com.mysql:mysql-connector-j")
//	testImplementation("org.springframework.boot:spring-boot-starter-test")
//	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
//	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
//	implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")
//	implementation("org.springframework.kafka:spring-kafka")
//	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

//	implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5:2.15.2")

	// QueryDSL 의존성 추가
	implementation("io.github.openfeign.querydsl:querydsl-jpa:6.10.1")
	kapt("io.github.openfeign.querydsl:querydsl-apt:6.10.1:jpa")
	kapt("jakarta.annotation:jakarta.annotation-api")
	kapt("jakarta.persistence:jakarta.persistence-api")

}
