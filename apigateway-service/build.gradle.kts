dependencies {
    implementation(project(":module-common:common-core"))

    // 1. Spring Cloud Gateway (핵심)
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")

    // 2. Eureka Client (서비스 위치 찾기용, 선택사항이지만 추천)
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
//    testImplementation(kotlin("test"))

    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
//    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Kotlin 필수 모듈 (Jackson, Reflect)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

//    testImplementation("org.springframework.boot:spring-boot-starter-test")

}

