plugins {
    kotlin("plugin.jpa")
    kotlin("kapt")
}

dependencies {
    implementation(project(":module-common:common-core"))
    implementation(project(":module-common:common-jpa"))
    implementation(project(":module-common:common-security"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")

    kapt("io.github.openfeign.querydsl:querydsl-apt:6.10.1:jpa")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")
}
