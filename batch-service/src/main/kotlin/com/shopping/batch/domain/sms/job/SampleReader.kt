package com.shopping.batch.domain.sms.job

import com.shopping.batch.domain.sms.dto.SmsSendDto
import com.shopping.batch.domain.sms.repository.SmsRecipientRepository
import org.springframework.batch.core.ItemReadListener
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@StepScope
class SampleReader(
    @Value("#{jobExecutionContext['sendInfoList']}")
    private val sendInfoList: List<SmsSendDto>,
    private val sendRecipientRepository: SmsRecipientRepository,
) : ItemReader<String>, ItemReadListener<String> {

    override fun beforeRead() {
        println("before sampleReader")
    }

    override fun afterRead(item: String) {
        println("after sampleReader")
    }

    override fun read(): String? {

        for(dto in sendInfoList) {
            val recipients = sendRecipientRepository.findDtoById(dto.id!!)
        }
        return null
    }
}