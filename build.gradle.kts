import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.4.2" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    kotlin("jvm") version "1.9.25" apply false
    kotlin("plugin.spring") version "1.9.25" apply false
    kotlin("plugin.jpa") version "1.9.25" apply false
    kotlin("kapt") version "1.9.25" apply false
}

// Spring Cloud 버전 변수 설정
val springCloudVersion = "2024.0.0"

allprojects {
    version = "1.0-SNAPSHOT"
    group = "com.shopping"

    repositories {
        mavenCentral()
    }
}

subprojects {

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "kotlin-kapt")

    dependencies {
        "implementation"("com.fasterxml.jackson.module:jackson-module-kotlin")
        "implementation"("org.jetbrains.kotlin:kotlin-reflect")
        "testImplementation"("org.jetbrains.kotlin:kotlin-test-junit5")
        "testImplementation"("org.springframework.boot:spring-boot-starter-test")
        "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
//        "implementation"("org.springframework.kafka:spring-kafka")
        "implementation"("io.github.microutils:kotlin-logging-jvm:3.0.5")

        // QueryDSL 의존성 추가
//        "implementation"("io.github.openfeign.querydsl:querydsl-jpa:6.10.1")
//        "kapt"("io.github.openfeign.querydsl:querydsl-apt:6.10.1:jpa")
//        "kapt"("jakarta.annotation:jakarta.annotation-api")
//        "kapt"("jakarta.persistence:jakarta.persistence-api")
    }

    configure<AllOpenExtension> {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.MappedSuperclass")
        annotation("jakarta.persistence.Embeddable")
    }

    // 3. 자바 버전 설정 (Kotlin 컴파일 옵션)
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    // 4. Spring Cloud BOM 설정 (버전 관리)
    configure<DependencyManagementExtension> {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

}




