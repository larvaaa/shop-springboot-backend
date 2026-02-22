
subprojects {
    // 이 모듈은 실행 파일이 아님을 명시
    tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        enabled = false
    }

    tasks.getByName<org.gradle.jvm.tasks.Jar>("jar") {
        enabled = true
    }

//    plugins {
//        // 1. 코틀린 기본 플러그인
//        kotlin("jvm")
//
//        // 2. ★ 이 플러그인이 필수입니다! ★
//        // 이 플러그인이 @Entity, @MappedSuperclass 등이 붙은 클래스를 자동으로 'open' 상태로 만들어줍니다.
//        kotlin("plugin.jpa") // (루트에서 버전을 관리한다면 version 생략 가능)
//    }

    dependencies {
        testImplementation(kotlin("test"))

        api("org.springframework.boot:spring-boot-starter-security")
    }

    // (참고) common은 실행 가능한 jar가 아니므로 bootJar 끄기
    tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        enabled = false
    }
    tasks.getByName<org.gradle.jvm.tasks.Jar>("jar") {
        enabled = true
    }
}