plugins {
    kotlin("kapt")
}

dependencies {
    testImplementation(kotlin("test"))

    api("org.springframework.boot:spring-boot-starter-data-jpa")

    // QueryDSL 의존성 추가
    api("io.github.openfeign.querydsl:querydsl-jpa:6.10.1")
    kapt("io.github.openfeign.querydsl:querydsl-apt:6.10.1:jpa")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")
}
