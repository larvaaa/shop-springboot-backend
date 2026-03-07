package com.shopping.batch.domain.sms.job

import com.shopping.batch.domain.sms.dto.SmsSendDto
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.database.JpaCursorItemReader
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDateTime

@Configuration
class SampleJobConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val sampleWriter: SampleWriter,
    private val entityManagerFactory: EntityManagerFactory
) {

    @Bean
    fun sampleJob(
        sampleStep: Step,
    ): Job =
        JobBuilder("sampleJob", jobRepository)
            .start(sampleStep)
            .build()

    @Bean
    fun sampleStep(): Step {

        return StepBuilder("sampleStep", jobRepository)
            .chunk<SmsSendDto, SmsSendDto>(30, transactionManager)
            .reader(sendReader(null))
            .writer(sampleWriter)
            .faultTolerant()
            .skip(IllegalStateException::class.java)
            .skipLimit(10)
            .retry(IllegalStateException::class.java)
            .retryLimit(3)
            .build()
    }

    @Bean
    @StepScope
    fun sendReader(
        @Value("#{jobParameters['now']}") now: LocalDateTime?
    ): JpaCursorItemReader<SmsSendDto> {

        return JpaCursorItemReaderBuilder<SmsSendDto>()
            .name("sampleReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("""
                select new com.shopping.batch.domain.sms.dto.SmsSendDto(
                    s.id,
                    s.content,
                    s.senderNumber,
                    s.status,
                    s.reservationTime,
                    r.id,
                    r.recipientNumber
                )
                  from SmsSend s
                  join fetch s.smsRecipients r
                 where s.status = 'READY'
                   and s.reservationTime <= :now
                 order by r.id
                """)
            .parameterValues(mapOf("now" to now))
            .build()

    }

}
