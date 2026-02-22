package com.shopping.batch.domain.sms.job

import com.shopping.batch.domain.sms.dto.SmsSendDto
import com.shopping.batch.domain.sms.repository.SmsSendRepository
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
@StepScope
class SampleWriter(
    private val smsSendRepository: SmsSendRepository
) : ItemWriter<SmsSendDto> {
    override fun write(chunk: Chunk<out SmsSendDto>) {

        println("chunk size: ${chunk.items.size}")
        val grouped: Map<Long, List<SmsSendDto>> = chunk.groupBy { item ->
            item.id!!
        }

        for((id, dto) in grouped) {

            smsSendRepository.updateSmsRecipient(dto)

        }
        println()
        println("==========================>  [ writer 종료 ]")
        println()
    }
}