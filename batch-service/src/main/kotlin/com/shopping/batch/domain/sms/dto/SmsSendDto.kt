package com.shopping.batch.domain.sms.dto

import com.querydsl.core.annotations.QueryProjection
import com.shopping.batch.domain.sms.entity.SendStatus
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDateTime

data class SmsSendDto @QueryProjection constructor (

    var id: Long? = null,

    val content: String,

    val senderNumber: String,

    val firstSendTime: LocalDateTime? = null,

    var lastSendTime: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    var status: SendStatus = SendStatus.READY,

    val reservationTime: LocalDateTime? = null,

    val recipientId: Long? = null,

    val recipientNumber: String? = null,
)
