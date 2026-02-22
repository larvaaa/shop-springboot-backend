package com.shopping.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@SpringBootApplication
@EnableJpaAuditing
class BatchApplication {

	//	@Bean
//	fun objectMapper(): ObjectMapper {
//		val mapper = ObjectMapper()
//		val hibernateModule = Hibernate5Module()
//			.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, false)
//			.configure(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true)
//		mapper.registerModule(hibernateModule)
//		return mapper
//	}

	@Bean
	fun auditorProvider(): AuditorAware<String> = AuditorAware {
		Optional.of("BATCH")
	}
}

fun main(args: Array<String>) {
	println("batch application run")
	args.forEach { println("args: ${it}") }
	runApplication<BatchApplication>(*args)
}

