package com.shopping.admin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
	scanBasePackages = [
		"com.shopping.admin",  // 1. 현재 서비스 패키지
		"com.common.core",
		"com.common.jpa",   // 2. 공통 모듈 패키지 (이걸 추가해야 Bean을 읽음)
		"com.common.security"
	]
)
class AdminApplication {

	//	@Bean
//	fun objectMapper(): ObjectMapper {
//		val mapper = ObjectMapper()
//		val hibernateModule = Hibernate5Module()
//			.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, false)
//			.configure(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true)
//		mapper.registerModule(hibernateModule)
//		return mapper
//	}

}

fun main(args: Array<String>) {
	runApplication<AdminApplication>(*args)
}

