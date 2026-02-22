package com.shopping.batch.domain.sms.job

import com.shopping.batch.domain.sms.repository.SmsSendRepository
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@StepScope
class SampleTasklet(
    @Value("#{jobParameters['time']}")
    private val time: String = "",

    private val smsSendRepository: SmsSendRepository,
) : Tasklet {

//    @Value("#{jobParameters['run.id']}")
//    private val runId: Long = 0L

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {

        println()
        println("================================================================> [ SampleTasklet 실행 ]")
        println()
        println("Job Parameters in contribution: ${contribution.stepExecution.jobParameters}") // {'no':'{value=2, type=class java.lang.String, identifying=true}','run.id':'{value=5, type=class java.lang.Long, identifying=true}'}
        println("Job Parameters in chunkContext: ${chunkContext.stepContext.jobParameters}") // {no=2, run.id=5}

        val params = chunkContext.stepContext.jobParameters
        println("params: $params")

        val result = smsSendRepository.findSmsInfo()
        println("result = ${result}")

        val jobExecution = chunkContext.stepContext.stepExecution.jobExecution
        jobExecution.executionContext.put("sendInfoList", result)

        return RepeatStatus.FINISHED
    }
}