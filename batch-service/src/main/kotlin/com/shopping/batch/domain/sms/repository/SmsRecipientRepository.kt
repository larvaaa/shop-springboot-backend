package com.shopping.batch.domain.sms.repository

import com.shopping.batch.domain.sms.dto.SmsRecipientDto
import com.shopping.batch.domain.sms.entity.SmsRecipient
import org.springframework.data.jpa.repository.JpaRepository

interface SmsRecipientRepository : JpaRepository<SmsRecipient, Long> {

    fun findDtoById(id: Long): SmsRecipientDto
}