package com.shopping.batch.domain.sms.dto

import com.shopping.batch.domain.sms.entity.SendStatus
import com.shopping.batch.domain.sms.entity.SmsSend
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDateTime

data class SmsRecipientDto(
    var id: Long? = null,

    var smsSend: SmsSend? = null,

    val recipientNumber: String,

    val sendTime: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    val status: SendStatus = SendStatus.READY
)
