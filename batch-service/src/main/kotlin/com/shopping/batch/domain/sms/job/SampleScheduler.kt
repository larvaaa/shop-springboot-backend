package com.shopping.batch.domain.sms.job

import com.shopping.batch.domain.sms.repository.SmsSendRepository
import mu.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDateTime

@EnableScheduling
class SampleScheduler(
    private val smsSendRepository: SmsSendRepository,
    private val jobLauncher: JobLauncher,
    private val sampleJob: Job,
) {

    private val logger = KotlinLogging.logger {}

    @Scheduled(cron = "0 * * * * *")
    fun sendSms() {

        logger.info {"[ 예약발송 스케줄러 실행 ]"}

        val now = LocalDateTime.now()

        val count = smsSendRepository.countSendInfo(now)

        logger.info {"[ 예약발송건수: ${count}건 ]"}

        if(count == 0) return

        try {
            val jobParameters = JobParametersBuilder()
                .addString("time", now.toString())
                .toJobParameters()

            logger.info {"[ 예약발송 job 실행 ]"}
            logger.info {"[ job 파라미터: {time=${now}} ]"}
            jobLauncher.run(sampleJob, jobParameters)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}